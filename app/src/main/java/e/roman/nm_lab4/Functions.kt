package e.roman.nm_lab4

import java.nio.channels.FileLock
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin
import kotlin.math.tan

fun f(x: Float, y: Float, z: Float): Float {
    return (1 + 2 * tan(x) * tan(x)) * y
}

fun f(x: Float): Float{
    return 1 / cos(x) + sin(x) + x / cos(x)
}

fun g(x: Float, y: Float, z: Float): Float{
    return z
}

fun delta(k: MutableList<Float>): Float {
    return (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6
}

fun triDiagonalSolve(a: MutableList<MutableList<Float>>): MutableList<Float> {
    val x = mutableListOf<Float>()
    val p = mutableListOf<Float>()
    val q = mutableListOf<Float>()
    p.add(-a[0][2] / a[0][1])
    q.add(a[0][3] / a[0][1])
    for (i in 1 .. a.lastIndex){
        p.add(-a[i][2] / (a[i][1] + a[i][0] * p[i - 1]))
        q.add((a[i][3] - a[i][0] * q[i - 1]) / (a[i][1] + a[i][0] * p[i - 1]))
    }
    x.add(q.last())
    for (i in a.lastIndex - 1 downTo 0)
        x.add(p[i] * x.last() + q[i])
    x.reverse()
    return x
}

fun p(x: Float): Float {
    return 0f
}

fun q(x :Float): Float{
    return -2f * (1 + tan(x) * tan(x))
}

fun f1(x: Float): Float {
    return 0f
}
/*
fun p(x: Float): Float {
    return 4 * x / (2 * x + 1)
}

fun q(x :Float): Float{
    return -4 / (2 * x + 1)
}

fun f1(x: Float): Float {
    return 0f
}*/

/*
def p(x):
return 4 * x / (2 * x + 1)


def q(x):
return -4 / (2 * x + 1)


def f(x):
return 0*/