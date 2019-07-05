import React from 'react';

import { Button, CardActionArea, CardActions, CardContent } from '@material-ui/core';

import StyledCarCard from "./StyledCarCard";

import carLogo from '../../../resources/car.svg';

function CarCard({ car }) {
    const picture = car.picture ? `data:image/jpeg;base64,${car.picture}` : carLogo;
    const pictureClassName = car.picture ? '' : 'defaultCarPicture';

    return (
        <StyledCarCard>
            <CardActionArea>
                <img alt={`Car n°${car.id}`} src={picture} className={pictureClassName} />
            </CardActionArea>

            <CardContent>
                {car.owner}<br/>
                {car.registration}<br/>
                {car.brand} {car.model}
            </CardContent>

            <CardActions>
                <Button>Open</Button>
            </CardActions>
        </StyledCarCard>
    );
}

export default CarCard;
