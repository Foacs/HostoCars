import React, { Fragment, PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Box, Button, Grid, Typography } from '@material-ui/core';

import { CarCard, ErrorPanel, LoadingPanel } from 'components';

import { changeCarsSortOrderAction, changeCurrentPageAction, changeSelectedMenuIndexAction, getCarsAction } from 'actions';

import { CarPropType } from 'resources';

import './CarsPage.scss';

class CarsPage extends PureComponent {
    constructor(props) {
        super(props);

        this.onRegistrationButtonClick = this.onRegistrationButtonClick.bind(this);
        this.onOwnerButtonClick = this.onOwnerButtonClick.bind(this);
    }

    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex, getCars, sortedBy } = this.props;

        changeCurrentPage('Voitures', []);

        changeSelectedMenuIndex(0);

        getCars(sortedBy);
    }

    onRegistrationButtonClick() {
        const { changeCarsSortOrder, getCars, sortedBy } = this.props;
        const registration = 'registration';

        if (sortedBy !== registration) {
            changeCarsSortOrder(registration);

            getCars(registration);
        }
    }

    onOwnerButtonClick() {
        const { changeCarsSortOrder, getCars, sortedBy } = this.props;
        const owner = 'owner';

        if (sortedBy !== owner) {
            changeCarsSortOrder(owner);

            getCars(owner);
        }
    }

    render() {
        const { cars, isInError, isLoading, sortedBy } = this.props;

        let content;
        if (isInError) {
            content = <ErrorPanel className='ErrorPanel' />;
        } else if (isLoading) {
            content = <LoadingPanel className='LoadingPanel' />;
        } else {
            const registrationButtonClassName = `SortSection-RegistrationButton ${sortedBy === 'registration'
            && 'SortSection-RegistrationButton_selected'}`;
            const ownerButtonClassName = `SortSection-OwnerButton ${sortedBy === 'owner'
            && 'SortSection-OwnerButton_selected'}`;

            content = (
                <Fragment>
                    <Box className='SortSection'>
                        <Button className={registrationButtonClassName} disableRipple
                                onClick={this.onRegistrationButtonClick}>Immatriculation</Button>
                        <Typography className='SortSection-Separator non-selectable' variant='h6'>|</Typography>
                        <Button className={ownerButtonClassName} disableRipple onClick={this.onOwnerButtonClick}>Propriétaire</Button>
                    </Box>

                    <Grid
                        className='CarsGrid'
                        container
                        justify='flex-start'
                        alignItems='flex-start'
                        spacing={4}>
                        {cars.map(car =>
                            <Grid className='CarsGrid-Item' item key={car.registration} lg={4} md={6} sm={12} xl={3} xs={12}>
                                <CarCard car={car} className='CarsGrid-Item-CarCard' />
                            </Grid>
                        )}
                    </Grid>
                </Fragment>
            );
        }

        return (
            <Box id='CarsPage'>
                {content}
            </Box>
        );
    }
}

const mapStateToProps = state => ({
    cars: state.cars.cars,
    sortedBy: state.cars.sortedBy,
    isLoading: state.cars.isLoading,
    isInError: state.cars.isInError
});

const mapDispatchToProps = dispatch => bindActionCreators({
    changeCarsSortOrder: changeCarsSortOrderAction,
        changeCurrentPage: changeCurrentPageAction,
        changeSelectedMenuIndex: changeSelectedMenuIndexAction,
        getCars: getCarsAction
    }, dispatch
);

CarsPage.propTypes = {
    cars: PropTypes.arrayOf(CarPropType).isRequired,
    sortedBy: PropTypes.string.isRequired,
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired,
    getCars: PropTypes.func.isRequired,
    isInError: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(CarsPage);
