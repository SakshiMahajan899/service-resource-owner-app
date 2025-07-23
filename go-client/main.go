package main

import (
    "bytes"
    "encoding/json"
    "flag"
    "fmt"
    "net/http"
    "sync"
    "time"
)

type Owner struct {
    ID            string `json:"id"`
    Name          string `json:"name"`
    AccountNumber string `json:"accountNumber"`
    Level         int    `json:"level"`
}

type Resource struct {
    ID     string  `json:"id"`
    Owners []Owner `json:"owners"`
}

type Service struct {
    ID        string     `json:"id"`
    Resources []Resource `json:"resources"`
}

var (
    parallelRequests int
    totalSteps       int
    apiURL           string
)

func init() {
    flag.IntVar(&parallelRequests, "parallel", 100, "Number of parallel requests per step")
    flag.IntVar(&totalSteps, "steps", 10, "Number of batch steps")
    flag.StringVar(&apiURL, "url", "http://localhost:8080/v1/services", "Target API URL")
    flag.Parse()
}

// Generates mock service data based on step and index
func buildServicePayload(step, index int) Service {
    owner := Owner{
        ID:            fmt.Sprintf("owner-%d-%d", step, index),
        Name:          fmt.Sprintf("Owner %d-%d", step, index),
        AccountNumber: fmt.Sprintf("ACC-%d-%d", step, index),
        Level:         index % 5,
    }

    resource := Resource{
        ID:     fmt.Sprintf("resource-%d-%d", step, index),
        Owners: []Owner{owner},
    }

    return Service{
        ID:        fmt.Sprintf("service-%d-%d", step, index),
        Resources: []Resource{resource},
    }
}

// Sends a single request
func sendRequest(service Service, wg *sync.WaitGroup) {
    defer wg.Done()

    payload, err := json.Marshal(service)
    if err != nil {
        fmt.Println("JSON marshal error:", err)
        return
    }

    resp, err := http.Post(apiURL, "application/json", bytes.NewBuffer(payload))
    if err != nil {
        fmt.Println("HTTP request error:", err)
        return
    }
    defer resp.Body.Close()

    fmt.Printf("→ Service %s sent. Status: %s\n", service.ID, resp.Status)
}

func main() {
    fmt.Println("Waiting for backend to be ready...")
    time.Sleep(10 * time.Second)
    fmt.Printf("Starting batch: %d steps × %d requests @ %s\n\n", totalSteps, parallelRequests, apiURL)

    for step := 1; step <= totalSteps; step++ {
        fmt.Printf(" Step %d\n", step)

        var wg sync.WaitGroup
        wg.Add(parallelRequests)

        for i := 1; i <= parallelRequests; i++ {
            service := buildServicePayload(step, i)
            go sendRequest(service, &wg)
        }

        wg.Wait()
        fmt.Printf(" Step %d complete\n\n", step)
    }

    fmt.Println(" All batches complete.")
}
