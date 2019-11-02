const lodash = require('lodash');

/**
 * Extracts the file name from its URL.
 * @param url The file URL
 * @returns {string} The file name
 */
export const extractFileNameFromURL = url => {
    const startIndex = (0 <= url.indexOf('\\') ? url.lastIndexOf('\\') : url.lastIndexOf('/'));
    let filename = url.substring(startIndex);
    if (0 === filename.indexOf('\\') || 0 === filename.indexOf('/')) {
        filename = filename.substring(1);
    }
    return filename;
};

/**
 * Formats a date to display the month as a string and the full year.
 * @param date The date to format
 * @returns {string} The formatted date
 */
export const formatDateLabel = date => {
    return null === date ? undefined : `${lodash.capitalize(date.toLocaleString('default', { month: 'long' }))} ${date.getFullYear()}`;
};

/**
 * Reads a file and converts it to a byte array.
 * @param file The file to read
 * @returns {[]} The result byte array
 */
export const loadFileAsByteArray = file => {
    let result = [];

    const reader = new FileReader();
    reader.readAsArrayBuffer(file);
    reader.onloadend = function (e) {
        if (FileReader.DONE === e.target.readyState) {
            const array = new Uint8Array(e.target.result);
            for (let i = 0; i < array.length; i++) {
                result.push(array[i]);
            }
        }
    };

    return result;
};
