# magic-aync-context
A magical Java thread pool that supports context transfer between threads or thread pools

## feature
1. 支持 `Runable`/ `Callerable`和线程池包装
2. 异步上下文传递

## 主线程和子线程上下文合并逻辑
![image](https://user-images.githubusercontent.com/6405415/183284071-e1188304-dcfd-4ec9-bb09-d231d030ac80.png)
子线程中途合并主线程上下文, 主线程上下文会属性会自动覆盖到子线程, 在attach结束之后, 恢复子线程上下文
