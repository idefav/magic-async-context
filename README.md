# magic-aync-context
A magical Java thread pool that supports context transfer between threads or thread pools

## Feature
1. Support for `Runable`,`Callable` and thread pool wrappers
2. Asynchronous thread context passing
3. Support integration with other contexts or ThreadLocal

## Main thread and child thread context merge logic
![image](https://user-images.githubusercontent.com/6405415/183284071-e1188304-dcfd-4ec9-bb09-d231d030ac80.png)
The child thread merges the main thread context in the middle, the main thread context attribute will be automatically overwritten to the child thread, and the child thread context will be restored after the attach ends.

## Quick Start
1. Import dependencies
```xml
<dependency>
  <groupId>com.idefav</groupId>
  <artifactId>magic-async-context</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```
2. Usage

* Context
```java
// Set value in Context
Context.current().put("name", name);

// Get the value from the Context, you can get the value in the child thread
Context.current().get("name")
```

* RequestContext
```java
// First get the Context from the ServletContext and set it to the Context
RequestHolder.set((HttpServletRequest) request)

// Get RequestURI
RequestHolder.get().getRequestURI()
```

* Demo

```java
    @GetMapping("/say/{name}")
    public String say(@PathVariable("name") String name) throws InterruptedException {
        Context.current().put("name", name);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(String.format("name: %s, requestUri:%s", Context.current()
                        .get("name"), RequestHolder.get().getRequestURI())); ;
            }
        });
        Thread.sleep(10);
        return String.format("hello, %s,RequestUri:%s", Context.current().get("name"), RequestHolder.get()
                .getRequestURI());
    }
```

3. Spring Boot Starter
```xml
<dependency>
    <groupId>com.idefav</groupId>
    <artifactId>magic-async-context-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency> 
```
Using Spring Boot Starter will automatically integrate RequestContext by default, you can directly use `RequestHolder.get().getRequestURI` to get request information
