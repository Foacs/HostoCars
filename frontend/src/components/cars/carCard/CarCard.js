import React from 'react';
import PropTypes from 'prop-types';

import { Box, CardActionArea, Divider, Typography } from '@material-ui/core';

import StyledCarCard from './StyledCarCard';

import { CarPropType, DefaultCarPicture } from 'resources';

function CarCard({ car, className }) {
    const componentClassName = `CarCard ${className}`;

    const picture = car.picture
        ? <img alt={`Car n°${car.id}`} src={`data:image/jpeg;base64,${car.picture}`} className='CarCard-ActionArea-PictureBox-Picture' />
        : <DefaultCarPicture className='CarCard-ActionArea-PictureBox-Picture CarCard-ActionArea-PictureBox-Picture_default' />;

    const modelTitleClassName = car.brand || car.model
        ? 'CarCard-ActionArea-TitleBox-ModelSubTitle'
        : 'CarCard-ActionArea-TitleBox-ModelSubTitle CarCard-ActionArea-TitleBox-ModelSubTitle_missing';
    const modelTitleContent = (car.brand || car.model)
        ? `${car.brand ? car.brand : ''} ${car.model ? car.model : ''}`
        : '-';

    return (
        <StyledCarCard className={componentClassName}>
            <CardActionArea className='CarCard-ActionArea'>
                <Box className='CarCard-ActionArea-PictureBox'>
                    {picture}
                </Box>

                <Divider variant='middle' className='CarCard-ActionArea-Divider' />

                <Box className='CarCard-ActionArea-TitleBox'>
                    <Typography align='center' noWrap variant='h6'
                                className='CarCard-ActionArea-TitleBox-RegistrationTitle'>{car.registration}</Typography>
                    <Typography align='center' noWrap variant='subtitle1'
                                className='CarCard-ActionArea-TitleBox-OwnerSubTitle'>{car.owner}</Typography>
                    <Typography align='center' noWrap variant='subtitle2' className={modelTitleClassName}>{modelTitleContent}</Typography>
                </Box>
            </CardActionArea>
        </StyledCarCard>
    );
}

CarCard.propTypes = {
    car: CarPropType.isRequired,
    className: PropTypes.string
};

CarCard.defaultProps = {
    className: ''
};

export default CarCard;
