var express = require('express');
var debug = require('debug')('http');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res) {
  res.render('index', { title: 'Express' });
});

/* GET users listing. */
router.get('/userlist', function(req, res) {
  var db = req.db;
  var users = db.get("users");
  users.find({},{},function(e,docs){
  	res.render('userlist', {
  		"userlist" : docs
  	});
  });
});

router.get('/newuser', function(req, res){
	res.render('newuser', {title: 'Add New User' });
});

/* POST to Add User Service */
router.post('/adduser', function(req, res) {
    var db = req.db;
    // Get our form values. These rely on the "name" attributes
    var userName = req.body.userName;
    var userEmail = req.body.userEmail;
    // Set our collection
    var users = db.get('users');
    // Submit to the DB
    users.insert({
        "userName" : userName,
        "userEmail" : userEmail
    }, function (err, doc) {
        if (err) {
            res.send("There was a problem adding the information to the database.");
        }
        else {
            res.location("userlist");
            res.redirect("userlist");
        }
    });
});

router.get('/deluser/:id', function(req, res){
	debug("receive the id=" + req.params.id);

	var db = req.db;
	var id = req.params.id;
	var users = db.get('users');

	users.remove({_id: id}, function(err){
		if(err){
			res.send("Some Problem during deleting the user with id = " + id);
		}
		else {
			res.location("/userlist");
			res.redirect("/userlist");
		}
	})
});

module.exports = router;
