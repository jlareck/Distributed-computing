package main
import (
	"fmt"
	"math"
	"sync"
	"time"
	"math/rand"
)
type Edge struct {
	to    int
	price int
}

type Graph [][]Edge

func generateGraph(N int) Graph {
	graph := make(Graph, N)
	visited := make([][]bool, N)
	for i := 0; i < N; i++ {
		graph[i] = make([]Edge, 0)
		visited[i] = make([]bool, N)
		for j := 0; j < N; j++ {
			visited[i][j] = false
			if i == j {
				visited[i][j] = true
			}
		}
	}
	for i := 0; i < N; i++ {
		for j := 0; j < N; j++ {
			price := generatePrice()
			if (rand.Intn(2) == 0) && (price != 0) && !visited[i][j] {
				graph[i] = append(graph[i], Edge{j, price})
				graph[j] = append(graph[j], Edge{i, price})
				visited[i][j] = true
				visited[j][i] = true
			}
		}
	}
	return graph
}

func generatePrice() int {
	return rand.Intn(10)
}

func numberOfTowns(graphLength int) int {
	return int(math.Sqrt(float64(1+8*graphLength))-1)/2 + 1
}

func generateNewEdge(graph Graph, N int) Graph {
	if N == 0 {
		return graph
	}
	visited := make([]bool, N)
	for i := 0; i < N; i++ {
		visited[i] = false
	}
	i := rand.Intn(N)
	for j := 0; j < len(graph[i]); j++ {
		if !visited[graph[i][j].to] {
			visited[graph[i][j].to] = true
		}
	}
	for j := 0; j < N; j++ {
		if !visited[j] {
			price := generatePrice() + 1
			graph[i] = append(graph[i], Edge{j, price})
			fmt.Println("New path ", i, " -> ", j, "\t\tprice\t", price)
			break
		}
	}

	return graph
}

func removeEdge(graph Graph, N int) Graph {
	if N != 0 {
		i := rand.Intn(N)
		if len(graph[i]) != 0 {
			j := rand.Intn(len(graph[i]))
			fmt.Println("Remove path ", i, " -> ", graph[i][j].to)
			graph[i] = append(graph[i][:j], graph[i][j+1:]...)
		}
	}
	return graph
}

func addTown(graph Graph) Graph {
	newTown := make([]Edge, 0)
	graph = append(graph, newTown)
	fmt.Println("New town ", len(graph)-1)
	return graph
}

func removeTown(graph Graph) Graph {
	N := len(graph)
	town := rand.Intn(N)
	graph = append(graph[:town], graph[town+1:]...)
	for i := 0; i < N-1; i++ {
		for j := 0; j < len(graph[i]); j++ {
			if graph[i][j].to == town {
				graph[i] = append(graph[i][:j], graph[i][j+1:]...)
			} else if graph[i][j].to > town {
				graph[i][j].to--
			}
		}
	}
	fmt.Println("Remove town ", town)
	return graph
}

func findPath(graph Graph, from int, to int) int {
	INF := math.MaxInt64
	N := len(graph)
	visited := make([]bool, N)
	prices := make([]int, N)
	for i := 0; i < N; i++ {
		visited[i] = false
		prices[i] = INF
	}

	prices[from] = 0

	for i := 0; i < N; i++ {
		v := -1
		for j := 0; j < N; j++ {
			if !visited[j] && (v == -1 || prices[j] < prices[v]) {
				v = j
			}
		}
		if prices[v] == INF {
			break
		}
		visited[v] = true
		for j := 0; j < len(graph[i]); j++ {
			to := graph[i][j].to
			price := graph[i][j].price
			if prices[v]+price < prices[to] {
				prices[to] = prices[v] + price
			}
		}
	}
	if prices[to] == INF {
		return 0
	}
	return prices[to]
}

func printGraph(graph Graph) {
	for i := 0; i < len(graph); i++ {
		fmt.Println(i, ": ", graph[i])
	}
}
func priceUpdater(graph Graph, readWriteLock sync.RWMutex) {
	for {
		readWriteLock.RLock()
		N := len(graph)
		if N != 0 {
			i := rand.Intn(N)
			if len(graph[i]) != 0 {
				j := rand.Intn(len(graph[i]))

				graph[i][j].price = generatePrice() + 1
				fmt.Println("Update ", i, " -> ", graph[i][j].to, "\t\tprice\t", graph[i][j].price)
			}
		}
		readWriteLock.RUnlock()
		randomWait()
	}
}

func edgeUpdater(graph Graph, readWriteLock sync.RWMutex) {
	for {
		readWriteLock.Lock()
		N := len(graph)
		if rand.Intn(2) == 0 {
			graph = generateNewEdge(graph, N)
		} else {
			graph = removeEdge(graph, N)
		}
		readWriteLock.Unlock()
		randomWait()
	}
}

func townUpdater(graph Graph, mutex sync.RWMutex) {
	for {
		mutex.Lock()
		if rand.Intn(2) == 0 {
			graph = addTown(graph)
		} else {
			graph = removeTown(graph)
		}
		mutex.Unlock()
		randomWait()
	}
}

func priceFinder(graph Graph, mutex sync.RWMutex) {
	for {
		mutex.RLock()
		N := numberOfTowns(len(graph))
		i := rand.Intn(N)
		j := rand.Intn(N)
		if i != j {
			price := findPath(graph, i, j)
			if price == 0 {
				fmt.Println("No path ", i, " -> ", j)
			} else {
				fmt.Println(i, " -> ", j, "\t\tprice\t", price)
			}
		}
		mutex.RUnlock()
		randomWait()
	}
}

func randomWait() {
	time.Sleep(time.Millisecond*100)
}

func main() {
	N := rand.Intn(8) + 5
	graph := generateGraph(N)
	printGraph(graph)
	readWriteLock := sync.RWMutex{}
	quit := make(chan bool)

	go priceUpdater(graph, readWriteLock)
	go edgeUpdater(graph, readWriteLock)
	go townUpdater(graph, readWriteLock)
	go priceFinder(graph, readWriteLock)

	<-quit
}