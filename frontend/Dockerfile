# Use the official Node.js image as the base image
FROM node:20.14-alpine

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json files to the container
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the Next.js project into the container
COPY . .

# Build the Next.js application
RUN npm run build 

# Expose the port Next.js runs on
EXPOSE 3000

# Command to run Next.js application
CMD ["npm", "start"]