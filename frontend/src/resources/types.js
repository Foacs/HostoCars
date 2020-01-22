import PropTypes from 'prop-types';

/**
 * Car prop type.
 */
export const CarPropType = PropTypes.shape({
    brand: PropTypes.string,
    certificate: PropTypes.blob,
    comments: PropTypes.string,
    id: PropTypes.number.isRequired,
    model: PropTypes.string,
    motorization: PropTypes.string,
    owner: PropTypes.string.isRequired,
    picture: PropTypes.blob,
    registration: PropTypes.string.isRequired,
    releaseDate: PropTypes.date
});
