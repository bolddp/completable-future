const express = require('express');
const demoRouter = require('./demo_router');

const port = 8080;

const app = express();
app.use('/', demoRouter());

app.listen(port, () => console.log(`Server listening on port ${port}!`));