import React, { Fragment, PureComponent } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import { bindActionCreators } from 'redux';
import PropTypes from 'prop-types';

import {
    Box, Button, CircularProgress, ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Grid, IconButton, Paper, Table, TableBody, TableCell,
    TableRow, Typography
} from '@material-ui/core';
import {
    EditRounded as EditIcon, ErrorOutlineRounded as ErrorIcon, SearchRounded as DisplayIcon, SentimentDissatisfiedRounded as SmileyIcon
} from '@material-ui/icons';

import {
    changeCurrentPageAction, changeSelectedMenuIndexAction, deleteCarAction, editCarAction, getCarAction, getCarRegistrationsAction
} from 'actions';
import { ErrorPanel, LoadingPanel } from 'components';
import { CertificateModal, DeleteCarModal, EditCarModal } from 'modals';
import { CarPropType, DefaultCarPicture, formatDateLabel } from 'resources';

import './CarPage.scss';

/**
 * The car page component.
 *
 * @param {object[]} cars
 *     The list of all the cars
 * @param {func} changeCurrentPage
 *     The {@link changeCurrentPageAction} action
 * @param {func} changeSelectedMenuIndex
 *     The {@link changeSelectedMenuIndexAction} action
 * @param {func} deleteCar
 *     The {@link deleteCarAction} action
 * @param {func} editCar
 *     The {@link editCarAction} action
 * @param {boolean} isInError
 *     If the car loading is in error
 * @param {boolean} isLoading
 *     If the car loading is in progress
 * @param {func} getCar
 *     The {@link getCarAction} action
 * @param {func} getCarRegistrations
 *     The {@link getCarRegistrationsAction} action
 * @param {object} match
 *     The URL parameters
 * @param {string[]} registrations
 *     The list of all the distinct car registration numbers
 *
 * @class
 */
class CarPage extends PureComponent {
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

        // Initializes the component state
        this.state = {
            car: undefined,
            hasCarBeenLoaded: false,
            isCertificateModalOpen: false,
            isDeleteModalOpen: false,
            isEditModalOpen: false,
            redirect: false
        };

        // Binds the local methods
        this.onCloseCertificateModal = this.onCloseCertificateModal.bind(this);
        this.onCloseDeleteCarModal = this.onCloseDeleteCarModal.bind(this);
        this.onCloseEditCarModal = this.onCloseEditCarModal.bind(this);
        this.onOpenCertificateModal = this.onOpenCertificateModal.bind(this);
        this.onOpenDeleteCarModal = this.onOpenDeleteCarModal.bind(this);
        this.onOpenEditCarModal = this.onOpenEditCarModal.bind(this);
        this.onValidateDeleteCarModal = this.onValidateDeleteCarModal.bind(this);
        this.onValidateEditCarModal = this.onValidateEditCarModal.bind(this);
        this.updateComponent = this.updateComponent.bind(this);
    }

    /**
     * Method called when the component did mount.
     */
    componentDidMount() {
        const { changeSelectedMenuIndex } = this.props;

        changeSelectedMenuIndex(0);

        this.updateComponent();
    }

    /**
     * Method called when the component did update.
     *
     * @param {object} prevProps
     *     The previous props
     */
    componentDidUpdate(prevProps) {
        const { match: { params: { id } } } = this.props;
        const { match: { params: { id: prevId } } } = prevProps;

        if (id !== prevId) {
            this.setState({
                car: undefined,
                hasCarBeenLoaded: false
            });
        }

        this.updateComponent();
    }

    /**
     * Handles the certificate modal close action.
     */
    onCloseCertificateModal() {
        this.setState({ isCertificateModalOpen: false });
    };

    /**
     * Handles the 'Delete car' modal close action.
     */
    onCloseDeleteCarModal() {
        this.setState({ isDeleteModalOpen: false });
    }

    /**
     * Handles the 'Edit car' modal close action.
     */
    onCloseEditCarModal() {
        this.setState({ isEditModalOpen: false });
    }

    /**
     * Handles the certificate button click action.
     */
    onOpenCertificateModal() {
        this.setState({ isCertificateModalOpen: true });
    };

    /**
     * Handles the 'Delete car' button click action.
     */
    onOpenDeleteCarModal() {
        this.setState({ isDeleteModalOpen: true });
    };

    /**
     * Handles the 'Edit car' button click action.
     */
    onOpenEditCarModal() {
        this.setState({ isEditModalOpen: true });
    };

    /**
     * Handles the 'Delete car' modal validate action.
     */
    async onValidateDeleteCarModal() {
        const { deleteCar } = this.props;
        const { car: { id } } = this.state;

        await deleteCar(id);
        this.setState({ redirect: true });
    }

    /**
     * Handles the 'Edit car' modal validate action.
     */
    onValidateEditCarModal(car) {
        const { editCar } = this.props;

        editCar(car);
    }

    /**
     * Updates the car page.
     */
    updateComponent() {
        const { hasCarBeenLoaded } = this.state;
        const { cars, changeCurrentPage, getCar, getCarRegistrations, isInError, isLoading, match: { params: { id } } } = this.props;

        let content;
        if (isInError) {
            // If the car or the registrations failed to be loaded, displays an error icon
            content = <ErrorIcon />;
        } else if (isLoading) {
            // If the car or the registrations are being loaded, displays a circular progress
            content = <CircularProgress size={20} thickness={4} />;
        } else {
            // If the car or the registrations are not being loaded nor failed to be, checks if they do have been loaded
            if (!cars || 0 === cars.length) {
                if (hasCarBeenLoaded) {
                    // If there is no car even after loading, displays the smiley icon
                    content = <SmileyIcon />;
                } else {
                    // If there is no car but it has yet to be loaded, loads it
                    getCar(id)
                            .then(getCarRegistrations())
                            .then(this.setState({ hasCarBeenLoaded: true }));
                    content = <CircularProgress size={20} thickness={4} />;
                }
            } else {
                // If there are cars, tries to get the current one
                const car = cars.find(car => Number(id) === car.id);

                if (car) {
                    // If the current car has been found, displays its registration
                    this.setState({ car });
                    content = car.registration;
                } else if (hasCarBeenLoaded) {
                    // If the current car has not been found even after loading, displays the smiley icon
                    content = <SmileyIcon />;
                } else {
                    // If the current car has not been found but has yet to be loaded, loads it
                    getCar(id)
                            .then(getCarRegistrations())
                            .then(this.setState({ hasCarBeenLoaded: true }));
                    content = <CircularProgress size={20} thickness={4} />;
                }
            }
        }

        // Updates the breadcrumbs
        changeCurrentPage(content, [ {
            label: 'Voitures',
            link: '/cars'
        } ]);
    }

    render() {
        const { cars, isInError, isLoading, registrations } = this.props;
        const { car, hasCarBeenLoaded, isCertificateModalOpen, isDeleteModalOpen, isEditModalOpen, redirect } = this.state;

        let content;
        if (redirect) {
            content = <Redirect to='/cars' />;
        } else if (isInError) {
            // If the cars failed to be loaded, displays the error panel
            content = <ErrorPanel />;
        } else if (isLoading) {
            // If the cars are being loaded, displays the loading panel
            content = <LoadingPanel />;
        } else if (car) {
            // If the car has been found, displays the car content
            const picture = car.picture ? <img alt={`Car n°${car.id}`} className='CarPicture' src={`data:image/jpeg;base64,${car.picture}`} /> :
                    <DefaultCarPicture className='CarPicture CarPicture_default' />;

            const certificateButton = <IconButton className='CertificateButton' onClick={this.onOpenCertificateModal}>
                <DisplayIcon />
            </IconButton>;

            const commentsSection = (<Fragment>
                <Grid item>
                    <Typography className='CommentsSubtitle' variant='subtitle1'>Commentaires</Typography>
                </Grid>

                <Grid item>
                    <Typography variant='body2'>{car.comments}</Typography>
                </Grid>
            </Fragment>);

            const existingRegistrations = registrations && registrations.filter(registration => car.registration !== registration);

            const serialNumbers = cars && cars.filter(currentCar => car.serialNumber !== currentCar.serialNumber)
                    .map(currentCar => currentCar.serialNumber);

            content = (<Fragment>
                <Grid container spacing={4}>
                    <Grid container item xs={6}>
                        <Grid item xs={12}>
                            <ExpansionPanel expanded>
                                <ExpansionPanelSummary className='InfoPanelHeader'>
                                    <Typography className='InfoPanelTitle' color='primary' variant='h6'>Informations</Typography>

                                    <IconButton className='EditButton' color='primary' onClick={this.onOpenEditCarModal}>
                                        <EditIcon />
                                    </IconButton>
                                </ExpansionPanelSummary>

                                <ExpansionPanelDetails>
                                    <Grid container direction='column' spacing={2}>
                                        <Grid item>
                                            <Table>
                                                <TableBody>
                                                    <TableRow className='TableRow' hover>
                                                        <TableCell>Propriétaire</TableCell>
                                                        <TableCell align='right'>{car.owner}</TableCell>
                                                    </TableRow>

                                                    <TableRow className='TableRow' hover>
                                                        <TableCell>Numéro d'immatriculation</TableCell>
                                                        <TableCell align='right'>{car.registration}</TableCell>
                                                    </TableRow>

                                                    <TableRow className='TableRow' hover>
                                                        <TableCell>VIN</TableCell>
                                                        <TableCell align='right'>{car.serialNumber}</TableCell>
                                                    </TableRow>

                                                    <TableRow className='TableRow' hover>
                                                        <TableCell>Marque</TableCell>
                                                        <TableCell align='right'>{car.brand}</TableCell>
                                                    </TableRow>

                                                    <TableRow className='TableRow' hover>
                                                        <TableCell>Modèle</TableCell>
                                                        <TableCell align='right'>{car.model}</TableCell>
                                                    </TableRow>

                                                    <TableRow className='TableRow' hover>
                                                        <TableCell>Motorisation</TableCell>
                                                        <TableCell align='right'>{car.motorization}</TableCell>
                                                    </TableRow>

                                                    <TableRow className='TableRow' hover>
                                                        <TableCell>Code moteur</TableCell>
                                                        <TableCell align='right'>{car.engineCode}</TableCell>
                                                    </TableRow>

                                                    <TableRow className='TableRow' hover>
                                                        <TableCell>Date de mise en circulation</TableCell>
                                                        <TableCell align='right'>{formatDateLabel(car.releaseDate)}</TableCell>
                                                    </TableRow>

                                                    <TableRow className='TableRow' hover>
                                                        <TableCell>Carte grise</TableCell>
                                                        <TableCell align='right' className='CertificateCell'>
                                                            {car.certificate && certificateButton}
                                                        </TableCell>
                                                    </TableRow>
                                                </TableBody>
                                            </Table>
                                        </Grid>

                                        {car.comments && commentsSection}
                                    </Grid>
                                </ExpansionPanelDetails>
                            </ExpansionPanel>
                        </Grid>
                    </Grid>

                    <Grid container item spacing={2} xs={6}>
                        <Grid item xs={12}>
                            <Paper className='PicturePanel'>
                                {picture}
                            </Paper>
                        </Grid>

                        <Grid alignItems='flex-end' container item xs={12}>
                            <Paper className='DeletePanel'>
                                <Button color='secondary' fullWidth onClick={this.onOpenDeleteCarModal}>
                                    Supprimer
                                </Button>
                            </Paper>
                        </Grid>
                    </Grid>
                </Grid>

                {car.certificate && <CertificateModal certificate={car.certificate} onClose={this.onCloseCertificateModal}
                                                      open={isCertificateModalOpen} />}

                <EditCarModal car={car} onClose={this.onCloseEditCarModal} open={isEditModalOpen} onValidate={this.onValidateEditCarModal}
                              registrations={existingRegistrations} serialNumbers={serialNumbers} />

                <DeleteCarModal onClose={this.onCloseDeleteCarModal} open={isDeleteModalOpen} onValidate={this.onValidateDeleteCarModal} />
            </Fragment>);
        } else if (!hasCarBeenLoaded) {
            // If the car has not been found but the cars have not been loaded yet, displays the loading panel
            content = <LoadingPanel />;
        } else {
            // If the car has not been found even after loading the cars, displays the 'Not found' content
            content = (<div className='NotFoundCar'>
                <SmileyIcon className='SmileyIcon' color='primary' />

                <Typography className='Label' color='primary' variant='h1'>Voiture introuvable</Typography>
            </div>);
        }

        return (<Box id='CarPage'>
            {content}
        </Box>);
    }
}

const mapStateToProps = (state) => ({
    cars: state.cars.cars,
    isInError: state.cars.isGetInError || state.cars.isGetRegistrationsInError,
    isLoading: state.cars.isGetInProgress || state.cars.isGetRegistrationsInProgress,
    registrations: state.cars.registrations
});

const mapDispatchToProps = (dispatch) => bindActionCreators({
    changeCurrentPage: changeCurrentPageAction,
    changeSelectedMenuIndex: changeSelectedMenuIndexAction,
    deleteCar: deleteCarAction,
    editCar: editCarAction,
    getCar: getCarAction,
    getCarRegistrations: getCarRegistrationsAction,
}, dispatch);

CarPage.propTypes = {
    cars: PropTypes.arrayOf(CarPropType).isRequired,
    changeCurrentPage: PropTypes.func.isRequired,
    changeSelectedMenuIndex: PropTypes.func.isRequired,
    deleteCar: PropTypes.func.isRequired,
    editCar: PropTypes.func.isRequired,
    isInError: PropTypes.bool.isRequired,
    isLoading: PropTypes.bool.isRequired,
    getCar: PropTypes.func.isRequired,
    getCarRegistrations: PropTypes.func.isRequired,
    match: PropTypes.shape({
        params: PropTypes.shape({
            id: PropTypes.string.isRequired
        }).isRequired
    }).isRequired,
    registrations: PropTypes.arrayOf(PropTypes.string.isRequired).isRequired
};

export default connect(mapStateToProps, mapDispatchToProps)(CarPage);
