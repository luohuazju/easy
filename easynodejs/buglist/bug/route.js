var express = require('express');
var router = express.Router();

var action = require('./action');

/* GET users listing. */
router.get('/', action.getAll);
router.post('/', action.create);
router.get('/:id', action.get);
router.put('/:id', action.update);
router.delete('/:id', action.del);

module.exports = router;