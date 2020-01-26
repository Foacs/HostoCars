import PropTypes from 'prop-types';

/**
 * Car prop type.
 *
 * @type {object}
 */
export const CarPropType = PropTypes.shape({
    brand: PropTypes.string,
    certificate: PropTypes.blob,
    comments: PropTypes.string,
    engineCode: PropTypes.string,
    id: PropTypes.number,
    model: PropTypes.string,
    motorization: PropTypes.string,
    owner: PropTypes.string,
    picture: PropTypes.blob,
    registration: PropTypes.string,
    releaseDate: PropTypes.date,
    serialNumber: PropTypes.string
});
