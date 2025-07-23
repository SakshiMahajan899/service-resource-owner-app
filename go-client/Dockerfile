# Use minimal Go image
FROM golang:1.21-alpine

# Optional: install git if you'll need it for dependencies
RUN apk update && apk add --no-cache git

# Set the working directory inside the container
WORKDIR /app

# Copy go.mod only for dependency resolution
COPY go.mod ./

# Generate go.sum and download dependencies
RUN go mod tidy

# Copy your full source code
COPY . .

# Build the binary
RUN go build -o batch-client main.go

# Default command to run your batch client
CMD ["./batch-client"]
