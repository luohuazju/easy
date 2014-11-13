var config = require('../config/config.json');
var mongodb = require('mongodb');
var poolModule = require('generic-pool');
var Memcached = require('memcached');
var log4js = require('log4js');

log4js.configure(__dirname +'/../config/log/log4js.json');
var logger = log4js.getLogger("root");

var pool = poolModule.Pool({
	name: 'mongo1',
	create: function(callback){
		var mongoClient = new mongodb.MongoClient(new mongodb.Server(config.host, config.port));
		mongoClient.open(function(err, mongoClient) {
  			var db = mongoClient.db(config.dbname);
			callback(err, db);
		});
	},
	destroy: function(db) { db.close(); },
	max: 10,
	min: 2,
	idleTimeoutMillis: 30000,
	log: false
});

//var memcached = new Memcached({'127.0.0.1:11211': 1, '127.0.0.1:11212': 1 })
var memcached = new Memcached({'127.0.0.1:11211': 1 })

// Returns all the bugs
exports.getAll = function(req, res) {
	pool.acquire(function(err, db){
		if (err) { 
            res.json(500,err);
        } else {
        	memcached.get("bugs", function(err, data){
        		if(data != false){
        			logger.debug("hitting the memached =" + data + "!");
        			res.json(data);
        		}else{
        			logger.debug("missing the memached!");
        			db.collection('bugs').find({}).toArray(function(err, bugs){
						if (err) res.json(500, err);
						else {
							logger.debug("setting the memached!");
							memcached.set("bugs", bugs ,120,function(err){ 
								if(err != undefined) res.json(500, err);
							});
							res.json(bugs);
						}
						pool.release(db);
					});
        		}
        	});
        }
	});
};

// Creates a bug
exports.create = function(req, res) {
	var body = req.body;
	console.log("post body  = " + JSON.stringify(body));

	pool.acquire(function(err, db){
		if (err) { 
            res.json(500,err);
        } else {
			db.collection('bugs').insert(body, function(err, bug){
				if (err) res.json(500, err);
				else res.json(201, bug);

				pool.release(db);
			});
        }
	});

};

// Get a bug
exports.get = function(req, res) {
	var id = req.params.id;

	pool.acquire(function(err, db){
		if (err) { 
            res.json(500,err);
        } else {
			db.collection('bugs').findOne({_id: new mongodb.ObjectID(id) }, function(err, bug){
				if (err) res.json(500, err);
				else if (bug) res.json(bug);
				else res.send(404);

				pool.release(db);
			});
        }
	});

};

// Updates a bug
exports.update = function(req, res) {
	var id = req.params.id;
	var body = req.body;
	delete body._id;

	pool.acquire(function(err, db){
		if (err) { 
            res.json(500,err);
        } else {
			db.collection('bugs').findAndModify({_id: new mongodb.ObjectID(id)}, [['_id','asc']], {$set: body}, {multi:false, new:true}, function(err, bug){
				if (err) res.json(500, err);
				else if (bug) res.json(bug);
				else res.send(404);

				pool.release(db);
			});
        }
	});
};

// Deletes a bug
exports.del = function(req, res) {
	var id = req.params.id;

	pool.acquire(function(err, db){
		if (err) { 
            res.json(500,err);
        } else {
			db.collection('bugs').remove({_id: new mongodb.ObjectID(id) }, function(err){
				if (err) res.json(500, err);
				else res.send(204);

				pool.release(db);
			});
        }
	});


};


