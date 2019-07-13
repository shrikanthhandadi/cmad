
### Setup steps

To setup your project

- Verify npm installed & check your version
```
npm version
```
Output
```
{ npm: '6.4.1',
  ares: '1.15.0',
  cldr: '33.1',
  http_parser: '2.8.0',
  icu: '62.1',
  modules: '64',
  napi: '3',
  nghttp2: '1.34.0',
  node: '10.15.3',
  openssl: '1.1.0j',
  tz: '2018e',
  unicode: '11.0',
  uv: '1.23.2',
  v8: '6.8.275.32-node.51',
  zlib: '1.2.11' }
```

- Switch to the project folder & init the project

```
npm init
```
Output
```
This utility will walk you through creating a package.json file.
It only covers the most common items, and tries to guess sensible defaults.

See `npm help json` for definitive documentation on these fields
and exactly what they do.

Use `npm install <pkg>` afterwards to install a package and
save it as a dependency in the package.json file.

Press ^C at any time to quit.
package name: (reactjs-basics) 
version: (1.0.0) 
description: ReactJS basics
entry point: (index.js) 
test command: 
git repository: 
keywords: reactjs
author: Shrikanth Handadi
license: (ISC) MIT
About to write to /Users/shandadi/git/cmad/ReactJS/reactjs-basics/package.json:

{
  "name": "reactjs-basics",
  "version": "1.0.0",
  "description": "ReactJS basics",
  "main": "index.js",
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1"
  },
  "keywords": [
    "reactjs"
  ],
  "author": "Shrikanth Handadi",
  "license": "MIT"
}


Is this OK? (yes) yes
```

- Add production dependency. This will update package.json -which is like pom.xml

```
npm install react react-dom --save
 ```
If you get error _npm ERR! Maximum call stack size exceeded_ execute ```rm -rf package-lock.json node_modules```

- Add development dependency

```
npm install webpack webpack-cli webpack-dev-server @babel/core babel-loader @babel/preset-env @babel/preset-stage-2 @babel/preset-react --save-dev
```

- Confirm the content which should look similar to below

```
cat package.json 
```
```
{
  "name": "reactjs-basics",
  "version": "1.0.0",
  "description": "ReactJS basics",
  "main": "index.js",
  "scripts": {
    "start": "npm run build",
    "build": "webpack -d && cp src/index.html dist/index.html && webpack-dev-server --port 3030 --content-base src/ --inline --hot",
    "build:prod": "webpack -d && cp src/index.html dist/index.html"
  },
  "keywords": [
    "reactjs"
  ],
  "author": "Shrikanth Handadi",
  "license": "MIT",
  "devDependencies": {
    "@babel/core": "^7.5.4",
    "@babel/preset-env": "^7.5.4",
    "babel-loader": "^8.0.6",
    "webpack": "^4.35.3",
    "webpack-cli": "^3.3.5",
    "webpack-dev-server": "^3.7.2"
  },
  "dependencies": {
    "react": "^16.8.6",
    "react-dom": "^16.8.6"
  }
}
```

- Create webpack.config.js with below content which is like compile & package task in maven pom.xml

```
cat webpack.config.js
```
```
var webpack = require("webpack");
var path = require("path");

var DIST_DIR = path.resolve(__dirname,"dist");
var SRC_DIR = path.resolve(__dirname,"src");

var config = {
		entry: SRC_DIR + "/app/index.js",
		output: {
			path: DIST_DIR + "/app",
			filename: "bundle.js",
			publicPath: "/app/"
		},
		module:{
			rules: [
				{
					test: /\.js?/,
					include: SRC_DIR,
					loader: "babel-loader",
					query:{
						presets: ["@babel/preset-env"]
					}
				}
			]
		}
};
module.exports = config;


```

- Update the scripts section to include start the server command. You can configure your choice of port here.

```
"scripts": {
 		"start": "npm run build",
		"build": "webpack -d && cp src/index.html dist/index.html && webpack-dev-server --port 3030 --content-base src/ --inline --hot",
		"build:prod": "webpack -d && cp src/index.html dist/index.html"
	},
```

- Create basic index.html & index.js files at below locations to confirm setup is working.

```
cat src/index.html 
```
```
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ReactJS Basics</title>
</head>
<body>
<div id="app"></div>
<script src="app/bundle.js"></script>
<p>Check console for log</p>
</body>
</html>
```
```
cat src/app/index.js 
```
```
console.log("It works");
```

- Now you can execute the whole setup
```
npm start
```

- Open below url & check in the console you are getting console log

```
http://localhost:3030
```

- For git checkins add .gitignore file as below
```
cat .gitignore
```
```
/node_modules/
/dist/
/package-lock.json
```
