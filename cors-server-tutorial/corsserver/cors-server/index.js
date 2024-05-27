const express = require('express');
const app = express();
const PORT = 5000;
const cors=require('cors');
app.use(cors())
app.get('/', (req, res) => {
    res.send("Welcome to CORS server! 😁")
})
app.get('/candy', (req, res) => {
    res.set('Access-Control-Allow-Origin', 'http://localhost:8000');
    res.json({ 'candy': 'bubble-gum' })
})
app.listen(PORT, () => console.log(`server running on port ${PORT}`))