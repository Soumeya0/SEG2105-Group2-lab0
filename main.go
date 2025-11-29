//SEG Lab6
//Group2
//Multithreading with Go Language
//Team members: 1- Soumaya 2- Destan 3-Ahmad



package main

import (
	"fmt"
	"io"
	"net/http"
	"sync"
	"time"
)

// --- Struct Definitions ---

// Job represents a single URL fetching task.
type Job struct {
	url string
}

// Result holds the data returned by a worker after fetching a URL.
type Result struct {
	url        string
	statusCode int
	contentSize int
	err        error
}

// --- Constants ---

const numWorkers = 5

// --- Worker Function ---

func worker(workerID int, jobs <-chan Job, results chan<- Result) {
	// Loop over the job channel until it's closed
	for job := range jobs {
		fmt.Printf("Worker %d starting job: %s\n", workerID, job.url)
		
		// 1. Create a Result struct
		result := Result{url: job.url}
		
		// 2. Fetch the URL
		client := http.Client{
			// Set a timeout to prevent hanging workers
			Timeout: 10 * time.Second,
		}

		resp, err := client.Get(job.url)
		if err != nil {
			// Record the error and status code as 0 (failed to connect/get response)
			result.err = err
			result.statusCode = 0
			// Send the result and continue to the next job
			results <- result
			continue
		}
		
		defer resp.Body.Close()
		
		// 3. Record Status Code
		result.statusCode = resp.StatusCode
		
		// 4. Record Content Size
		// Read the entire body to get the size and prepare for closure.
		size, err := io.Copy(io.Discard, resp.Body)
		if err != nil {
			// This typically happens if the connection closes prematurely
			result.err = fmt.Errorf("error reading content: %w", err)
		}
		result.contentSize = int(size)
		
		fmt.Printf("Worker %d finished job: %s (Status: %d)\n", workerID, job.url, result.statusCode)
		
		// 5. Send Result to the results channel
		results <- result
	}
}

// --- Main Logic ---

func main() {
	// The list of URLs to scrape
	urls := []string{
		"https://www.google.com",
		"https://go.dev",
		"https://www.wikipedia.org",
		"https://httpbin.org/status/404", // Intentional 404
		"https://www.reddit.com",
		"https://golang.org",
		"https://asdfghjkl12345.com",      // Intentional DNS error
		"http://localhost:9999",          // Intentional connection timeout
		"https://www.youtube.com",
		"https://github.com",
	}

	// 1. Setup Channels and WaitGroup
	jobs := make(chan Job, len(urls))
	results := make(chan Result, len(urls))
	var wg sync.WaitGroup

	fmt.Printf("--- Concurrent Web Scraper Initialized ---\n")
	fmt.Printf("Total URLs to process: %d\n", len(urls))
	fmt.Printf("Fixed worker pool size: %d\n\n", numWorkers)
    
    // Display the list of URLs (as required for demonstration)
    fmt.Println("URLs List:")
    for i, url := range urls {
        fmt.Printf("  %d: %s\n", i+1, url)
    }
    fmt.Println("------------------------------------------")

	// 2. Start Worker Goroutines (Worker Pool)
	for w := 1; w <= numWorkers; w++ {
		wg.Add(1)
		go func(id int) {
			// Decrement the counter when the worker finishes (i.e., when jobs channel closes)
			defer wg.Done()
			worker(id, jobs, results)
		}(w)
	}

	// 3. Dispatch Jobs
	for _, url := range urls {
		jobs <- Job{url: url}
	}
	close(jobs) // Close the jobs channel when all jobs are dispatched

	// 4. Wait for all workers to finish
	go func() {
		wg.Wait()
		close(results) // Close the results channel once all workers have finished
	}()

	// 5. Collect and Display Results
	fmt.Printf("\n--- Final Results ---\n")
	for result := range results {
		status := fmt.Sprintf("%d", result.statusCode)
		size := fmt.Sprintf("%d bytes", result.contentSize)
		
		if result.err != nil {
			status = "N/A"
			size = "N/A"
			fmt.Printf("❌ Failed: %s | Status: %s | Size: %s | Error: %v\n", result.url, status, size, result.err)
		} else {
			fmt.Printf("✅ Success: %s | Status: %d | Size: %d bytes\n", result.url, result.statusCode, result.contentSize)
		}
	}

	fmt.Printf("--- All Downloads Finished ---\n")
}