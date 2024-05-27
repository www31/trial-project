const express = require('express');
const cors = require('cors');

const server = express();
server.use(cors());

// simulate delayed response
server.use((req, res, next) => {
    setTimeout(() => next(), Math.floor(Math.random() * 5000) + 1);
});

server.get('/api/search/:searchTerm', async (req, res, next) => {
    res.send({
        searchTerm: req.params.searchTerm,
        results: Math.floor(Math.random() * 5000) + 1,
    });
});

server.listen('3000', () => {
    console.log(`Server is listening on PORT 3000`);
});