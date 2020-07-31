def foo():
    (yield 1)

def bar():
    (yield 1, 2, 3)

def foo_bar():
    (yield from (x for x in range(100)))
