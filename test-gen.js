const
	YAML = require('yamljs'),
	fs = require("fs"),
	stt = require("swagger-test-templates");

const config = {
  assertionFormat: "expect",
  testModule: 'supertest',
  pathName: [],
  loadTest: [
  				{pathName:'/api/users/1', operation:'get', load:{requests: 1000, concurrent: 100}},
  				{pathName:'/webglux/api/users/1', operation:'get', load:{requests: 1000, concurrent: 100}}
  			],
  maxLen: 80,
  pathParams: {
  }
};

const yml = fs.readdirSync('target/swagger/');
yml.forEach(function(s){

	let swagger = YAML.load('target/swagger/' + s);

	let tests = stt.testGen(swagger, config);

	if (!fs.existsSync('./target/generated-sources/test'))
		fs.mkdirSync('./target/generated-sources/test');

	tests.forEach(function (element) {
		fs.writeFileSync("./target/generated-sources/test/" + element.name, element.test, (err) => {
			if (err) throw err;
			console.log(element.name, " written");
		});
	}, this);
});
