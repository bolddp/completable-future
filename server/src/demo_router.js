const express = require('express');

module.exports = (router = express.Router()) => {

  let customerIndex = 0;

  const customers = [
    {id: 1, name: 'Customer1' },
    {id: 2, name: 'Customer2' }
  ];

  router.get('/customers', (req, res) => {
    setTimeout(() =>  {
      customerIndex += 2;
      res.status(200).json([
        {id: customerIndex, name: `Customer ${customerIndex}` },
        {id: customerIndex+1, name: `Customer ${customerIndex+1}` }
      ]);
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
