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
    releaseDate: PropTypes.string,
    serialNumber: PropTypes.string
});

/**
 * Intervention prop type.
 *
 * @type {object}
 */
export const InterventionPropType = PropTypes.shape({
    amount: PropTypes.number,
    carId: PropTypes.number,
    comments: PropTypes.string,
    creationYear: PropTypes.number,
    description: PropTypes.string,
    estimatedTime: PropTypes.number,
    id: PropTypes.number,
    mileage: PropTypes.number,
    number: PropTypes.number,
    paidAmount: PropTypes.number,
    realTime: PropTypes.number,
    status: PropTypes.string
});

/**
 * Operation prop type.
 *
 * @type {object}
 */
export const OperationPropType = PropTypes.shape({
    id: PropTypes.number,
    interventionId: PropTypes.number,
    type: PropTypes.string
});

/**
 * Operation line prop type.
 *
 * @type {object}
 */
export const OperationLinePropType = PropTypes.shape({
    description: PropTypes.string,
    done: PropTypes.bool,
    id: PropTypes.number,
    operationId: PropTypes.number
});
