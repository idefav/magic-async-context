# magic-aync-context
A magical Java thread pool that supports context transfer between threads or thread pools

## Feature
1. Support for `Runable`,`Callerable` and thread pool wrappers
2. Asynchronous thread context passing
3. Support integration with other contexts or ThreadLocal

## Main thread and child thread context merge logic
![image](https://user-images.githubusercontent.com/6405415/183284071-e1188304-dcfd-4ec9-bb09-d231d030ac80.png)
The child thread merges the main thread context in the middle, the main thread context attribute will be automatically overwritten to the child thread, and the child thread context will be restored after the attach ends.

## Quick Start
1. 引入依赖
```xml
<dependency>
  <groupId>com.idefav</groupId>
  <artifactId>magic-async-context</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```
2. 使用方式
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
