/**
 * Created by bigz on 31.05.17.
 */


import java.security.SecureRandom


val N1 = 10000
val N2 = 1000000


val a = 3.0
val k = 3
val c = 2.8
val t = 1.96 //gamma = 0.95

val rnd = SecureRandom()

fun calculate1(n: Int) {
    val f = { x: Double -> Math.log(a * x + 1) }
    val rand = { from: Double, to: Double -> from + rnd.nextDouble() * (to - from) }
    //Монте-Карло
    val inside = (0..n - 1).count {
        (0..k - 1).map {
            f(rand(0.0, 1.0))
        }.sum() <= c
    }.toDouble()
    //Вычисления
    val volume = inside / n
    val dispersion = Math.sqrt(inside * (1 - volume) / (n - 1))
    val error = t * dispersion / Math.sqrt(n.toDouble())
    val from = volume - error
    val to = volume + error
    //Вывод
    println(" Объем выборки n: ${n}")
    println(String.format(" Объем части тела: %.8f", volume))
    println(String.format(" Дисперсия: %.8f", dispersion))
    println(String.format(" Погрешность: %.8f", error))
    println(String.format(" Доверительный итервал: [%.8f, %.8f]", from, to))
}


fun calculate2(n: Int) {
    val wolframVolume = 0.116901
    val f = { x: Double -> Math.pow(3.0, -Math.pow(x, 2.0)) }
    val from = 1.0
    val to = 4.0
    val rand = { from: Double, to: Double -> from + rnd.nextDouble() * (to - from) }
    //Монте-Карло
    val points = List(n, { rand(from, to) })
    val sum = points.map { f(it) }.sum()
    //Вычисления
    val volume = sum / n
    val sumSquares = points.sumByDouble { Math.pow(volume - f(it), 2.0) }
    val dispersion = Math.sqrt(sumSquares / (n - 1))
    val error = t * dispersion / Math.sqrt(n.toDouble())
    val fromInt = volume - error
    val toInt = volume + error
    //Вывод
    println(String.format(" Объем выборки n: %d", n))
    println(String.format(" Значение интеграла: %.8f", volume * (to - from)))
    println(String.format(" Значение интеграла с wolframalpha: %.8f", wolframVolume))
    println(String.format(" Дисперсия: %.8f", dispersion * (to - from)))
    println(String.format(" Погрешность: %.8f", error))
    println(String.format(" Доверительный итервал: [%.8f, %.8f]", fromInt, toInt))
}

fun calculate3(n: Int) {
    val wolframVolume = 0.284347
    val lambda = 2.0
    val rand = { -1 / lambda * Math.log(1 - rnd.nextDouble()); }
    val f = { x: Double -> Math.pow(x, (2.0 / 3)) }
    //Монте-Карло
    val points = List(n, { rand() })
    val sum = points.map { f(it) }.sum()
    //Вычисления
    val volume = sum / n / lambda
    val sumSquares = points.sumByDouble { Math.pow(volume - f(it), 2.0) }
    val dispersion = Math.sqrt(sumSquares / (n - 1))
    val error = t * dispersion / Math.sqrt(n.toDouble()) / lambda
    val from = volume - error
    val to = volume + error
    //Вывод
    println(String.format(" Объем выборки n: %d", n))
    println(String.format(" Значение интеграла: %.8f", volume))
    println(String.format(" Значение интеграла с wolframalpha: %.8f", wolframVolume))
    println(String.format(" Дисперсия: %.8f", dispersion))
    println(String.format(" Погрешность: %.8f", error))
    println(String.format(" Доверительный итервал: [%.8f, %.8f]", from, to))
}

fun main(args: Array<String>) {
    println("Задание 1:")
    println("${N1} итераций")
    calculate1(N1)
    println("${N2} итераций")
    calculate1(N2)
    println("Задание 2:")
    println("${N1} итераций")
    calculate2(N1)
    println("${N2} итераций")
    calculate2(N2)
    println("Задание 3:")
    println("${N1} итераций")
    calculate3(N1)
    println("${N2} итераций")
    calculate3(N2)
}


/*
Задание 1:
10000 итераций
 Объем выборки n: 10000
 Объем части тела: 0,62890000
 Дисперсия: 0,48312331
 Погрешность: 0,00946922
 Доверительный итервал: [0,61943078, 0,63836922]
1000000 итераций
 Объем выборки n: 1000000
 Объем части тела: 0,62882700
 Дисперсия: 0,48311886
 Погрешность: 0,00094691
 Доверительный итервал: [0,62788009, 0,62977391]
Задание 2:
10000 итераций
 Объем выборки n: 10000
 Значение интеграла: 0,11335946
 Значение интеграла с wolframalpha: 0,11690100
 Дисперсия: 0,22231472
 Погрешность: 0,00145246
 Доверительный итервал: [0,03633403, 0,03923894]
1000000 итераций
 Объем выборки n: 1000000
 Значение интеграла: 0,11723953
 Значение интеграла с wolframalpha: 0,11690100
 Дисперсия: 0,22628173
 Погрешность: 0,00014784
 Доверительный итервал: [0,03893201, 0,03922768]
Задание 3:
10000 итераций
 Объем выборки n: 10000
 Значение интеграла: 0,28438781
 Значение интеграла с wolframalpha: 0,28434700
 Дисперсия: 0,48155268
 Погрешность: 0,00471922
 Доверительный итервал: [0,27966859, 0,28910702]
1000000 итераций
 Объем выборки n: 1000000
 Значение интеграла: 0,28410885
 Значение интеграла с wolframalpha: 0,28434700
 Дисперсия: 0,47882871
 Погрешность: 0,00046925
 Доверительный итервал: [0,28363960, 0,28457811]
 */

