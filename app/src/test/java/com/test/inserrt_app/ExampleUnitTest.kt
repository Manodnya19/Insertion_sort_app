import org.junit.Test
import org.junit.Assert.assertEquals
import java.util.*

class SortingUnitTest {

    @Test
    fun testSorting() {
        val inputArray = intArrayOf(5, 2, 8, 1, 9, 3)

        val sortedArray = insertionSort(inputArray)

        val isSorted = isSortedInIncreasingOrder(sortedArray)
        assertEquals(true, isSorted)
    }

    private fun insertionSort(arr: IntArray): IntArray {
        val n = arr.size
        for (i in 1 until n) {
            val key = arr[i]
            var j = i - 1
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]
                j--
            }
            arr[j + 1] = key
        }
        return arr
    }

    private fun isSortedInIncreasingOrder(arr: IntArray): Boolean {
        for (i in 0 until arr.size - 1) {
            if (arr[i] > arr[i + 1]) {
                return false
            }
        }
        return true
    }
}
