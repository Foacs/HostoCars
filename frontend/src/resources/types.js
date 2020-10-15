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
    interventions: PropTypes.arrayOf(PropTypes.object),
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
    car: PropTypes.object,
    carId: PropTypes.number,
    carRegistration: PropTypes.string,
    comments: PropTypes.string,
    description: PropTypes.string,
    estimatedTime: PropTypes.number,
    id: PropTypes.number,
    mileage: PropTypes.number,
    number: PropTypes.number,
    operations: PropTypes.arrayOf(PropTypes.object),
    paidAmount: PropTypes.number,
    realTime: PropTypes.number,
    status: PropTypes.string,
    year: PropTypes.number
});

/**
 * Operation prop type.
 *
 * @type {object}
 */
export const OperationPropType = PropTypes.shape({
    id: PropTypes.number,
    intervention: PropTypes.object,
    label: PropTypes.string,
    operationLines: PropTypes.arrayOf(PropTypes.object)
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
    operation: PropTypes.object,
    type: PropTypes.string
});
