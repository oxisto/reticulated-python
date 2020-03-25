#/usr/bin/env python3
import math
n = 1000
euler_bricks = [
    [c, b, a]
    for a in range(1, n)
    for b in range(1, a)
    if math.sqrt( a*a + b*b)%1 == 0
    for c in range(1, b)
    if math.sqrt( b*b + c*c)%1 == 0 and
       math.sqrt(a*a + c*c)%1 == 0
]
print(
    sorted(
        euler_bricks
    )
)