import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

import { Box, Card, CardActionArea, Divider, Typography } from '@material-ui/core';

import { CarPropType, DefaultCarPicture } from 'resources';

import './CarCard.scss';

/**
 * Car card component.
 *
 * @param car
 *     The card's car
 * @param className
 *     The component class name
 */
function CarCard({ car, className }) {
    /**
     * Defines the model subtitle class name depending on the car's brand and model values.
     */
    const modelSubtitleClassName = `ModelSubTitle ${(!car.brand && !car.model) && 'ModelSubTitle_missing'}`;

    /**
     * Defines the model subtitle label depending on the car's brand and model values.
     */
    const modelSubtitleLabel = (car.brand || car.model) ? `${car.brand ? car.brand : ''} ${car.model ? car.model : ''}` : '-';

    /**
     * Defines the car's picture depending on its value.
     */
    const picture = car.picture ? <img alt={`Car n°${car.id}`} src={`data:image/jpeg;base64,${car.picture}`} className='Picture' /> :
        <DefaultCarPicture className='Picture Picture_default' />;

    return (<Card className={className} id='CarCard'>
        <CardActionArea component={Link} to={`/cars/${car.id}`}>
            <Box className='PictureBox'>
                {picture}
            </Box>

            <Divider variant='middle' />

            <Box className='TitleBox'>
                <Typography align='center' noWrap variant='h6' className='RegistrationTitle'>{car.registration}</Typography>

                <Typography align='center' noWrap variant='subtitle1' className='OwnerSubTitle'>{car.owner}</Typography>

                <Typography align='center' noWrap variant='subtitle2' className={modelSubtitleClassName}>{modelSubtitleLabel}</Typography>
            </Box>
        </CardActionArea>
    </Card>);
}

CarCard.propTypes = {
    car: CarPropType.isRequired,
    className: PropTypes.string
};

CarCard.defaultProps = {
    className: ''
};

export default CarCard;
