import React, { Fragment, PureComponent } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import { Box, Button, Grid, Typography } from '@material-ui/core';
import { AddCircleOutlineRounded as AddIcon } from '@material-ui/icons';

import { addCarAction, changeCarsSortOrderAction, changeCurrentPageAction, changeSelectedMenuIndexAction, getCarsAction } from 'actions';
import { CarCard, ErrorPanel, LoadingPanel } from 'components';
import { AddCarModal } from 'modals';
import { CarPropType } from 'resources';

import './CarsOverviewPage.scss';

/**
 * Cars overview page component.
 */
class CarsOverviewPage extends PureComponent {
    /**
     * Constructor.
     *
     * @param props
     *     The component props
     */
    constructor(props) {
        super(props);

        // Initializes the component state
        this.state = { isAddCarModalOpen: false };

        // Binds the local methods
        this.onCloseAddCarModal = this.onCloseAddCarModal.bind(this);
        this.onOpenAddCarModal = this.onOpenAddCarModal.bind(this);
        this.onOwnerButtonClick = this.onOwnerButtonClick.bind(this);
        this.onRegistrationButtonClick = this.onRegistrationButtonClick.bind(this);
        this.onValidateAddCarModal = this.onValidateAddCarModal.bind(this);
    }

    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { changeCurrentPage, changeSelectedMenuIndex, getCars, sortedBy } = this.props;

        changeCurrentPage('Voitures', []);
        changeSelectedMenuIndex(0);

        getCars(sortedBy);
    }

    /**
     * Handles the 'Add car' modal close action.
     */
    onCloseAddCarModal() {
        this.setState({ isAddCarModalOpen: false });
    };

    /**
     * Handles the 'Add car' button click action.
     */
    onOpenAddCarModal() {
        this.setState({ isAddCarModalOpen: true });
    };

    /**
     * Handles the 'Sort by owner' button click action.
     */
    onOwnerButtonClick() {
        const { changeCarsSortOrder, getCars, sortedBy } = this.props;
        const owner = 'owner';

        if (sortedBy !== owner) {
            changeCarsSortOrder(owner);
            getCars(owner);
        }
    }

    /**
     * Handles the 'Sort by registration' button click action.
     */
    onRegistrationButtonClick() {
        const { changeCarsSortOrder, getCars, sortedBy } = this.props;
        const registration = 'registration';

        if (sortedBy !== registration) {
            changeCarsSortOrder(registration);
            getCars(registration);
        }
    }

    /**
     * Handles the 'Add car' modal validate action.
     */
    onValidateAddCarModal(car) {
        const { addCar, sortedBy } = this.props;

        addCar(car, sortedBy);
    }

    /**
     * Render method.
     */
    render() {
        const { cars, isInError, isLoading, sortedBy } = this.props;
        const { isAddCarModalOpen } = this.state;

        let content;
        if (isInError) {
            // If the cars failed to be loaded, displays the error panel
            content = <ErrorPanel className='ErrorPanel' />;
        } else if (isLoading) {
            // If the cars are being loaded, displays the loading panel
            content = <LoadingPanel className='LoadingPanel' />;
        } else {
            // If the cars have been loaded, displays the page normal content
            content = (<Fragment>
                <Grid alignItems='center' className='HeaderGrid' container justify='space-between'>
                    <Grid item>
                        <Button className='AddCarButton' color='primary' onClick={this.onOpenAddCarModal} variant='outlined'>
                            Ajouter

                            <AddIcon className='AddCarIcon' />
                        </Button>
                    </Grid>

                    <Grid item>
                        <Box className='SortSection'>
                            <Button className='RegistrationButton' color={'registration' === sortedBy ? 'primary' : 'inherit'} disableRipple
                                    onClick={this.onRegistrationButtonClick}>Immatriculation</Button>

                            <Typography className='Separator non-selectable' variant='h6'>|</Typography>

                            <Button className='OwnerButton' color={'owner' === sortedBy ? 'primary' : 'inherit'} disableRipple
                                    onClick={this.onOwnerButtonClick}>Propriétaire</Button>
                        </Box>
                    </Grid>
                </Grid>

                <Grid alignItems='flex-start' container justify='flex-start' spacing={4}>
                    {cars.map(car => <Grid item key={car.registration} lg={4} md={6} sm={12} xl={3} xs={12}>
                        <CarCard car={car} className='CarCard' />
                    </Grid>)}
                </Grid>

                <AddCarModal onClose={this.onCloseAddCarModal} open={isAddCarModalOpen} onValidate={this.onValidateAddCarModal}
                             registrations={cars.map(car => car.registration)} />
            </Fragment>);
        }

        return (<Box id='CarsOverviewPage'>
            {content}
        </Box>);
    }
}

const mapStateToProps = state => ({
    cars: state.cars.cars,
    isInError: state.cars.isGetAllInError,
    isLoading: state.cars.isGetAllInProgress,
    sortedBy: state.cars.sortedBy
});

const mapDispatchToProps = dispatch => bindActionCreators({
    addCar: addCarAction,
    changeCarsSortOrder: changeCarsSortOrderAction,
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction,
    getCars: getCarsAction
}, dispatch);

CarsOverviewPage.propTypes = {
    addCar: PropTypes.func.isRequired,
    cars: PropTypes.arrayOf(CarPropType).isRequired,
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired,
    getCars: PropTypes.func.isRequired,
    isInError: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired,
    sortedBy: PropTypes.string.isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(CarsOverviewPage);
