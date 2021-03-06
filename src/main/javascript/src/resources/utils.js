const lodash = require('lodash');

/**
 * Format a number by adding leading zeros in order to match the specified size.
 *
 * @param {number} number
 *     The number to format
 * @param {number} size
 *     The target size
 *
 * @returns {string} the formatted number as string
 */
export const addLeadingZeros = (number, size) => {
    return lodash.padStart(number, size, 0);
};

/**
 * Compares the given interventions.
 * <br/>
 * <br/>
 * The comparison is performed on the interventions' year and number fields.
 *
 * @param intervention1
 *     The first intervention
 * @param intervention2
 *     The second intervention
 *
 * @returns {number} the result of the comparison
 */
export const compareInterventions = (intervention1, intervention2) => {
    if (intervention1.year > intervention2.year) {
        return 1;
    } else if (intervention1.year < intervention2.year) {
        return -1;
    } else {
        if (intervention1.number > intervention2.number) {
            return 1;
        } else if (intervention1.number < intervention2.number) {
            return -1;
        } else {
            return 0;
        }
    }
};

/**
 * Extracts the entity ID from its URL.
 *
 * @param {string} url
 *     The file URL
 *
 * @returns {string} the entity ID
 */
export const extractEntityIdFromUrl = (url) => {
    const words = url.split('/');
    return words[words.length - 1];
};

/**
 * Extracts the file name from its URL.
 *
 * @param {string} url
 *     The file URL
 *
 * @returns {string} the file name
 */
export const extractFileNameFromUrl = (url) => {
    const startIndex = (0 <= url.indexOf('\\') ? url.lastIndexOf('\\') : url.lastIndexOf('/'));
    let filename = url.substring(startIndex);
    if (0 === filename.indexOf('\\') || 0 === filename.indexOf('/')) {
        filename = filename.substring(1);
    }
    return filename;
};

/**
 * Formats a date to display the month as a string and the full year.
 *
 * @param {date} date
 *     The date to format
 *
 * @returns {string} the formatted date
 */
export const formatDateLabel = (date) => {
    const dateObject = new Date(date);
    return null === date ? undefined : `${lodash.capitalize(dateObject.toLocaleString('fr', { month: 'long' }))} ${dateObject.getFullYear()}`;
};

/**
 * Returns a random number generated from the current date time.
 *
 * @returns {number} the generated random number
 */
export const generateRandomNumber = () => {
    return new Date().getTime() * Math.random();
};

/**
 * Reads a file and converts it to a byte array.
 *
 * @param {*} file
 *     The file to read
 *
 * @returns {[]} the result byte array
 */
export const loadFileAsByteArray = (file) => {
    let result = [];

    const reader = new FileReader();
    reader.readAsArrayBuffer(file);
    reader.onloadend = (e) => {
        if (FileReader.DONE === e.target.readyState) {
            const array = new Uint8Array(e.target.result);
            for (let item of array) {
                result.push(item);
            }
        }
    };

    return result;
};

/**
 * Stops the given event propagation.
 *
 * @param {object} e
 *     The event to stop the propagation
 */
export const stopPropagation = (e) => {
    e.stopPropagation();
};
