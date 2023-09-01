package wtf.speech.features.crypto.domain.usecases

import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger

object Base58 {
    private val encodeTable = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray()
    private val reverseTable = computeReverseTable()
    val fiftyEight: BigInteger = BigInteger.valueOf(58)
    fun encode(hex: String?): String {
        return encode(Hex.decode(hex))
    }

    fun getSymbolValue(c: Char): Int {
        return reverseTable[c.code and 0xFF]
    }

    fun getValueSymbol(v: Int): Char {
        return encodeTable[v]
    }

    fun encode(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (i in bytes.indices) {
            if (bytes[i].toInt() == 0) {
                sb.append(1)
            } else {
                break
            }
        }
        val temp = ByteArray(bytes.size + 1)
        for (i in 1 until temp.size) {
            temp[i] = bytes[i - 1]
        }
        var n = BigInteger(temp)
        val d = fiftyEight
        var result: Array<BigInteger>
        val sb2 = StringBuilder()
        while (n.compareTo(BigInteger.ZERO) != 0) {
            result = n.divideAndRemainder(d)
            val div = result[0]
            val rem = result[1]
            n = div
            sb2.append(getValueSymbol(rem.toInt()))
        }
        return sb.toString() + sb2.reverse().toString()
    }

    fun decode(hex: String): ByteArray {
        var zeros = 0
        for (element in hex) {
            if (element == '1') {
                zeros++
            } else {
                break
            }
        }
        var n = BigInteger.ZERO
        val d = fiftyEight
        val encoded = hex.substring(zeros)
        for (element in encoded) {
            n = n.multiply(d)
            n = n.add(BigInteger.valueOf(getSymbolValue(element).toLong()))
        }
        val temp = n.toByteArray()
        val temp2 = if (temp[0].toInt() == 0) {
            ByteArray(temp.size - 1 + zeros)
        } else {
            ByteArray(temp.size + zeros)
        }
        for (i in 0.coerceAtLeast(temp.size - temp2.size) until temp.size) {
            temp2[i - temp.size + temp2.size] = temp[i]
        }
        return temp2
    }

    fun getPrefixValue(prefix: String): Long {
        if (prefix.length > 10) {
            return -1
        }
        var total: Long = 0
        var value: Long = 1
        for (i in prefix.length - 1 downTo 0) {
            val v = getSymbolValue(prefix[i])
            if (v == -1) {
                return -1
            }
            total += value * v
            value *= 58
        }
        return total
    }

    fun computeReverseTable(): IntArray {
        val table = IntArray(256)
        for (i in 0..255) {
            table[i] = -1
        }
        for (i in encodeTable.indices) {
            table[encodeTable[i].code and 0xFF] = i
        }
        return table
    }
}