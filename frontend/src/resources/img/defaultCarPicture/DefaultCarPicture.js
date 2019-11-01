import PropTypes from 'prop-types';
import React from 'react';

import './DefaultCarPicture.scss';

function DefaultCarPicture({ className }) {
    // noinspection LongLine
    return <svg className={className} id='DefaultCarPicture' viewBox='0 0 66.1 53.88'>
        <path className='Background'
              d='M15.34 12.31c.71-1.56 1.44-3.16 2-4.29 1.42-3.02 5.55-3.61 7.34-3.61h20.16c1.8 0 5.93.54 7.38 3.61s4.3 9.53 4.3 9.53 3.43-2 6.3-2 5 3.59 1.26 6.48c-1.73 1.2-5.06 1.07-6.44.77 0 0 5 3.26 5.08 6.2s-2 16.55-2 16.55h-2.83v5.08a3.23 3.23 0 0 1-3.24 3.23h-2.82a3.23 3.23 0 0 1-3.23-3.23v-5.06H20.93v5.08a3.23 3.23 0 0 1-3.24 3.23h-2.82a3.23 3.23 0 0 1-3.23-3.23v-5.08H8.81S6.68 32 6.8 29s5.09-6.2 5.09-6.2c-1.39.3-4.71.43-6.44-.77-3.78-2.89-1.63-6.48 1.26-6.48s6.29 2 6.29 2' />

        <path className='Outline'
              d='M12.92 8.9c.71-1.57 1.43-3.17 2-4.3C16.34 1.54 20.46 1 22.26 1h20.16c1.79 0 5.92.54 7.37 3.6s4.31 9.53 4.31 9.53 3.42-2 6.29-2 5 3.59 1.26 6.47c-1.73 1.2-5 1.08-6.43.77 0 0 5 3.26 5.08 6.2s-2 16.56-2 16.56h-2.84v5.07a3.23 3.23 0 0 1-3.23 3.24h-2.82a3.24 3.24 0 0 1-3.24-3.24v-5.04H18.5v5.07a3.23 3.23 0 0 1-3.23 3.24h-2.82a3.24 3.24 0 0 1-3.24-3.24v-5.07H6.38s-2.12-13.6-2-16.56 5.08-6.2 5.08-6.2c-1.38.31-4.71.43-6.43-.77-3.79-2.88-1.63-6.47 1.25-6.47s6.3 2 6.3 2' />

        <path className='Windshield'
              d='M49.79 16.37h-34.9a65.72 65.72 0 0 1 3.5-8 5.1 5.1 0 0 1 4.48-2.54h19a5.1 5.1 0 0 1 4.42 2.57 65.72 65.72 0 0 1 3.5 7.97z' />

        <circle className='Headlight' cx='13.22' cy='27.89' r='3.58' />

        <circle className='Headlight' cx='51.46' cy='27.89' r='3.58' />

        <path className='Smile' d='M23 33.23a24.85 24.85 0 0 0 10.27 2.71A21 21 0 0 0 43 33.23' />
    </svg>;
}

DefaultCarPicture.propTypes = {
    className: PropTypes.string
};

DefaultCarPicture.defaultProps = {
    className: ''
};

export default DefaultCarPicture;
