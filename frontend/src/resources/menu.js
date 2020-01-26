import React from 'react';

import { DirectionsCarRounded as CarsIcon, ListAltRounded as InterventionsIcon } from '@material-ui/icons';

/**
 * The application menu items.
 *
 * @type {object[]}
 */
export const menuItems = [ {
    label: 'Voitures',
    link: '/cars',
    icon: <CarsIcon />
}, {
    label: 'Interventions',
    link: '/interventions',
    icon: <InterventionsIcon />
} ];
