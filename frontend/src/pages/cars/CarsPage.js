import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Grid } from '@material-ui/core';

import { CarCard, ErrorPanel, LoadingPanel } from 'components';

import StyledCarsPage from './StyledCarsPage';

import { changeCurrentPageAction, changeSelectedMenuIndexAction, getCarsAction } from 'actions';

import { CarPropType } from 'resources';

class CarsPage extends PureComponent {
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex, getCars } = this.props;

        changeCurrentPage('Voitures', []);

        changeSelectedMenuIndex(0);

        getCars();
    }

    render() {
        const { cars, isInError, isLoading } = this.props;

        let content;
        if (isInError) {
            content = <ErrorPanel className='CarsPage-ErrorPanel' />;
        } else if (isLoading) {
            content = <LoadingPanel className='CarsPage-LoadingPanel' />;
        } else {
            content = (
                <Grid
                    className='CarsPage-CarsGrid'
                    container
                    justify='flex-start'
                    alignItems='flex-start'
                    spacing={4}>
                    {cars.map(car =>
                        <Grid className='CarsPage-CarsGrid-Item' item lg={4} md={6} sm={12} xl={3} xs={12}>
                            <CarCard car={car} className='CarsPage-CarsGrid-Item-CarCard' />
                        </Grid>
                    )}
                </Grid>
            );
        }

        return (
            <StyledCarsPage className='CarsPage'>
                {content}
            </StyledCarsPage>
        );
    }
}

const mapStateToProps = state => ({
    cars: state.cars.cars,
    isLoading: state.cars.isLoading,
    isInError: state.cars.isInError
});

const mapDispatchToProps = dispatch => bindActionCreators({
        changeCurrentPage: changeCurrentPageAction,
        changeSelectedMenuIndex: changeSelectedMenuIndexAction,
        getCars: getCarsAction
    }, dispatch
);

CarsPage.propTypes = {
    cars: PropTypes.arrayOf(CarPropType).isRequired,
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
