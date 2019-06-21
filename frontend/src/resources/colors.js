/* eslint-disable import/no-webpack-loader-syntax */
const colors = require('sass-extract-loader?{"plugins": ["sass-extract-js"]}!./colors.scss');
/* eslint-enable import/no-webpack-loader-syntax */

module.exports = colors;
