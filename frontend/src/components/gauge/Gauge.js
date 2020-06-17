import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

import { Gauge as GaugeJS } from 'gaugeJS';

import './Gauge.scss';

/**
 * The options of the gauge canvas.
 */
const options = {
    angle: -0.3,
    generateGradient: true,
    highDpiSupport: true,
    limitMax: false,
    limitMin: false,
    lineWidth: 0.1,
    percentColors: [
        [ 0.0, '#F44336' ],
        [ 0.5, '#FFC107' ],
        [ 1.0, '#4CAF50' ]
    ],
    pointer: {
        length: 0.6
    },
    radiusScale: 0.6
};

/**
 * The gauge component.
 *
 * @param [className = '']
 *     The component class name
 * @param maxValue
 *     The maximum value
 * @param value
 *     The current value
 *
 * @constructor
 */
class Gauge extends PureComponent {
    /**
     * Constructor.
     *
     * @param {object} props
     *     The component props
     *
     * @constructor
     */
    constructor(props) {
        super(props);

        // Create the reference for the gauge canvas
        this.canvasRef = React.createRef();
    }

    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { maxValue, value } = this.props;

        // Render the gauge canvas
        const canvas = this.canvasRef.current;
        if (null !== canvas) {
            const gauge = new GaugeJS(canvas).setOptions(options);
            gauge.animationSpeed = 30;
            gauge.setMinValue(0);
            gauge.maxValue = maxValue;
            gauge.set(value);
        }
    }

    /**
     * Render method.
     */
    render() {
        const { className } = this.props;

        return (<canvas className={className} id='Gauge' ref={this.canvasRef} />);
    }
}

Gauge.propTypes = {
    className: PropTypes.string,
    maxValue: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired
};

Gauge.defaultProps = {
    className: ''
};

export default Gauge;
