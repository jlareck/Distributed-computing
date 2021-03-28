package main
import "sync"
import (
	"fmt"
	"math/rand"
	"time"
)
type CyclicBarrier struct {
	generation int
	count      int
	parties    int
	condition  *sync.Cond
	f          func()
}

func (b *CyclicBarrier) nextGeneration() {
	b.condition.Broadcast()
	b.count = b.parties
	b.generation++
}

func (b *CyclicBarrier) Await(action func()) {
	b.condition.L.Lock()

	defer b.condition.L.Unlock()
	defer b.f()
	defer action()

	generation := b.generation

	b.count--
	index := b.count

	if index == 0 {
		b.nextGeneration()
	} else {
		for generation == b.generation {
			b.condition.Wait()
		}
	}
}

func NewCyclicBarrier(num int, f func()) *CyclicBarrier {
	b := CyclicBarrier{}
	b.count = num
	b.parties = num
	b.condition = sync.NewCond(&sync.Mutex{})
	b.f = f
	return &b
}


type SumArray struct {
	data [10]int
	sum  int
}

const upperLimit = 10

var seed = rand.NewSource(time.Now().UnixNano())
var random = rand.New(seed)

func NewSumArray() *SumArray {
	var res = SumArray{}
	res.data = [10]int{}
	res.sum = 0
	return &res
}

func (sumArray *SumArray) Fill() {
	var sum = 0
	for i := range sumArray.data {
		sumArray.data[i] = random.Int() % upperLimit
		sum += sumArray.data[i]
	}
	sumArray.sum = sum
}

func (sumArray *SumArray) Replace() {
	var pos = random.Int() % len(sumArray.data)
	var change = random.Intn(2) == 0
	if change {
		if sumArray.data[pos] == upperLimit {
			return
		}
		sumArray.sum += 1
		sumArray.data[pos] += 1
	} else {
		if sumArray.data[pos] == 0 {
			return
		}
		sumArray.sum -= 1
		sumArray.data[pos] -= 1
	}
}

func (sumArray *SumArray) Print() {
	fmt.Println(sumArray.data, " ", sumArray.sum)
}


const SIZE = 500
const MAX = 100

func worker(pos int, g []SumArray, halt chan int, barrier *CyclicBarrier) {
	for {
		g[pos].Replace()

		barrier.Await(func() {
			if checkEquality(g) {
				halt <- 1
			}
		})

	}
}

func checkEquality(g []SumArray) bool {
	var firstVal = g[0].sum
	for i := 1; i < len(g); i++ {
		if firstVal != g[i].sum {
			return false
		}
	}
	return true
}

func Print(g []SumArray) {
	for _, e := range g {
		e.Print()
	}
	fmt.Println()
}

func main() {
	var g []SumArray
	var halt = make(chan int)
	var barrier = NewCyclicBarrier(1, func() {
		Print(g)
	})
	for i := 0; i < 3; i++ {
		var s = SumArray{}
		s.Fill()
		g = append(g, s)
	}

	for i := range g {
		go worker(i, g, halt, barrier)
	}

	<-halt

}