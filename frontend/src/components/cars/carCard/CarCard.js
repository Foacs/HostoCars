import { Box, Card, CardActionArea, Divider, Typography } from '@material-ui/core';
import PropTypes from 'prop-types';
import React from 'react';

import { CarPropType, DefaultCarPicture } from 'resources';

import './CarCard.scss';

function CarCard({ car, className }) {
    const picture = car.picture ? <img alt={`Car n°${car.id}`} src={`data:image/jpeg;base64,${car.picture}`}
                                       className='ActionArea-PictureBox-Picture' /> : <DefaultCarPicture
        className='ActionArea-PictureBox-Picture ActionArea-PictureBox-Picture_default' />;

    const modelTitleClassName = car.brand || car.model ? 'ActionArea-TitleBox-ModelSubTitle'
        : 'ActionArea-TitleBox-ModelSubTitle ActionArea-TitleBox-ModelSubTitle_missing';
    const modelTitleContent = (car.brand || car.model) ? `${car.brand ? car.brand : ''} ${car.model ? car.model : ''}` : '-';

    return <Card id='CarCard' className={className}>
        <CardActionArea className='ActionArea'>
            <Box className='ActionArea-PictureBox'>
                {picture}
            </Box>

            <Divider variant='middle' className='ActionArea-Divider' />

            <Box className='ActionArea-TitleBox'>
                <Typography align='center' noWrap variant='h6'
                            className='ActionArea-TitleBox-RegistrationTitle'>{car.registration}</Typography>
                <Typography align='center' noWrap variant='subtitle1'
                            className='ActionArea-TitleBox-OwnerSubTitle'>{car.owner}</Typography>
                <Typography align='center' noWrap variant='subtitle2' className={modelTitleClassName}>{modelTitleContent}</Typography>
            </Box>
        </CardActionArea>
    </Card>;
}

CarCard.propTypes = {
    car: CarPropType.isRequired,
    className: PropTypes.string
};

CarCard.defaultProps = {
    className: ''
};

export default CarCard;
