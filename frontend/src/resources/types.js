import PropTypes from 'prop-types';

// Breadcrumbs' navigation path prop type
export const NavigationPathPropType = PropTypes.arrayOf(PropTypes.shape({
    label: PropTypes.string.isRequired,
    link: PropTypes.string.isRequired
}));

// Car prop type
export const CarPropType = PropTypes.shape({
    id: PropTypes.number.isRequired,
    owner: PropTypes.string.isRequired,
    registration: PropTypes.string.isRequired,
    brand: PropTypes.string,
    model: PropTypes.string,
    motorization: PropTypes.string,
    releaseDate: PropTypes.date,
    certificate: PropTypes.blob,
    comments: PropTypes.string,
    picture: PropTypes.blob
});
