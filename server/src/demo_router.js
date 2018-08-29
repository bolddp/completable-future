const express = require('express');

module.exports = (router = express.Router()) => {

  const customers = [
    {id: 1, name: 'Customer1' },
    {id: 2, name: 'Customer2' }
  ];

  router.get('/customers', (req, res) => {
    setTimeout(() =>  {
      res.status(200).json(customers);
    }, 500);
  });

  router.get('/customers/:id', (req, res) => {
    setTimeout(() => {
      const customer = customers.find(x => x.id === req.query.id);
      if (!customer) {
        res.status(404).send();
      }
      res.send(customer);
    }, 500);
  });

  return router;
};
