#/usr/bin/env python3

def func(param, *args, **kwargs, ):
    print(param, args, kwargs, )

def fun_func(*args_f, **kwargs_f, ):
    func("a", "1", "2", *args_f, **kwargs_f, )

fun_func("$", "%", kwargs1 = "test1", kwargs2 = "test2", )