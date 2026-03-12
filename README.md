# 🚀 url-shortener
This API is a URL Shortener made using Java, JDBC for the database connection, PostgreSQL, jUnit5 and Mockito for unit testing,
Git and GitHub for version control and Docker for containerization.

## 💻 Technologies
- Language: Java 25
- Database: PostgreSQL 17
- Tests: jUnit5 & Mockito
- JSON Parsing: jackson

## 📦 Installation

#### Requirements
- Docker
- Docker Compose

#### How to install

Run the command below in the terminal.
```bash
cp .env.example .env
docker compose up -d --build
```
Note: You can change the credentials modifying the .env file

<details>
<summary><h1>🛤️ Routes</h1></summary>

## Shorten️
<span style="color: #9315BD;">POST</span> http://localhost:8080/shorten
### Request
```json5
{
  "originalUrl": "https://validurl.com"
}
```

### Response

#### Success
Status: 201 Created

```json5
{
  "data": {
    "shortenedUrl": "http://localhost:8080/GRl7BJ",
    "originalUrl": "https://validurl.com"
  },
  "success": true,
  "message": "URL shortened"
}
```
<details>
<summary><h4>Failures</h4></summary>
Status: 400 Bad Request

```json5
{
  "data": "",
  "success": false,
  "message": "Invalid URL"
}
```
When the URL is not a valid URL.

Status: 400 Bad Request

```json5
{
  "data": "",
  "success": false,
  "message": "Unexpected character ('}' (code 125)): expected a value"
}
```
When your JSON structure is not correct

Status: 400 Bad Request

```json5
{
  "data": "",
  "success": false,
  "message": "Unrecognized Field"
}
```
When you send an invalid field in the request

Status: 400 Bad Request
```json5
{
  "data": "",
  "success": false,
  "message": "Invalid data type"
}
```
When you don't send any Body
</details>

## Shortcode

<span style="color: green;">GET</span> http://localhost:8080/{shortcode}

### Success

Status: 302 Found

It doesn't return any Body, only the response to redirect

### Failure

Status: 404 Not Found

```json5
{
  "data": "",
  "success": false,
  "message": "Error 404: Page Not Found"
}
```
When an invalid Shortcode was provided

</details>

<h1 style="text-align: center;">📳 Networking</h1>

<div style="text-align: center;">
  <a href="https://github.com/GabrielUS19">
    <img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" alt="GitHub">
  </a>
  <a href="https://www.linkedin.com/in/gabriel-uchoa-de-sousa-9911662ba/">
    <img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn">
  </a> 
  <a href="mailto:gabrieluchoa2607@gmail.com">
    <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail">
  </a>
</div>
