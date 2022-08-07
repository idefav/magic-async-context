# magic-aync-context
A magical Java thread pool that supports context transfer between threads or thread pools

## feature
1. Support for `Runable`,`Callerable` and thread pool wrappers
2. Asynchronous thread context passing

## Main thread and child thread context merge logic
![image](https://user-images.githubusercontent.com/6405415/183284071-e1188304-dcfd-4ec9-bb09-d231d030ac80.png)
The child thread merges the main thread context in the middle, the main thread context attribute will be automatically overwritten to the child thread, and the child thread context will be restored after the attach ends.
