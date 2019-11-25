import React from 'react';
import PropTypes from 'prop-types';

import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    FormControl,
    Grid,
    IconButton,
    InputAdornment,
    InputLabel,
    OutlinedInput,
    TextField,
    TextFieldProps
} from '@material-ui/core';
import { CancelRounded as CancelIcon, FolderOpenRounded as BrowseIcon, HelpOutlineRounded as HelpIcon } from '@material-ui/icons';
import { DatePicker } from '@material-ui/pickers';

import { extractFileNameFromURL, formatDateLabel, loadFileAsByteArray } from 'resources';

import './EditCarModal.scss';

/**
 * The certificate field value label when the current car has a certificate.
 * @type {string}
 */
const currentCertificateLabel = 'Carte grise actuelle';

/**
 * The certificate field value label when the current car has an image.
 * @type {string}
 */
const currentImageLabel = 'Image actuelle';

/**
 * The text to display when the required owner field is not provided.
 * @type {string}
 */
const ownerRequiredHelperText = 'Veuillez renseigner le nom du propriétaire';

/**
 * The text to display when the required registration field is not provided.
 * @type {string}
 */
const registrationRequiredHelperText = 'Veuillez renseigner l\'immatriculation';

/**
 * The text to display when the registration field already exists.
 * @type {string}
 */
const registrationUniqueHelperText = 'Ce numéro d\'immatriculation existe déjà';

/**
 * Modal to edit an existing car.
 *
 * @param car
 *     The car being edited
 * @param className
 *     The component class name
 * @param onClose
 *     The close event handler
 * @param onValidate
 *     The validate event handler
 * @param open
 *     If the modal is open
 * @param registrations
 *     The list of existing registrations (except the one of the current car)
 */
function EditCarModal({ car, className, onClose, onValidate, open, registrations }) {
    // Initializes the help flag
    const [ help, setHelp ] = React.useState(false);

    // Initializes the car fields
    const [ owner, setOwner ] = React.useState(car.owner);
    const [ registration, setRegistration ] = React.useState(car.registration);
    const [ brand, setBrand ] = React.useState(car.brand ? car.brand : '');
    const [ model, setModel ] = React.useState(car.model ? car.model : '');
    const [ motorization, setMotorization ] = React.useState(car.motorization ? car.motorization : '');
    const [ releaseDate, setReleaseDate ] = React.useState(car.releaseDate);
    const [ certificate, setCertificate ] = React.useState(car.certificate);
    const [ picture, setPicture ] = React.useState(car.picture);
    const [ comments, setComments ] = React.useState(car.comments ? car.comments : '');

    // Initializes the labels for the uploaded files
    const [ certificateFileName, setCertificateFileName ] = React.useState(car.certificate ? currentCertificateLabel : '');
    const [ pictureFileName, setPictureFileName ] = React.useState(car.picture ? currentImageLabel : '');

    // Initializes the constraints
    const [ ownerRequired, setOwnerRequired ] = React.useState(false);
    const [ registrationRequired, setRegistrationRequired ] = React.useState(false);
    const [ registrationUnique, setRegistrationUnique ] = React.useState(false);

    /**
     * Clears the form.
     */
    const clearForm = () => {
        setOwner(car.owner);
        setRegistration(car.registration);
        setBrand(car.brand ? car.brand : '');
        setModel(car.model ? car.model : '');
        setMotorization(car.motorization ? car.motorization : '');
        setReleaseDate(car.releaseDate);
        setCertificate(car.certificate);
        setPicture(car.picture);
        setComments(car.comments ? car.comments : '');

        setCertificateFileName(car.certificate ? currentCertificateLabel : '');
        setPictureFileName(car.picture ? currentImageLabel : '');

        setOwnerRequired(false);
        setRegistrationRequired(false);
        setRegistrationUnique(false);
    };

    /**
     * Handles the dialog close action or the cancel button click action.
     */
    const onCancelAction = () => {
        clearForm();
        onClose();
    };

    /**
     * Handles the clear action for the given field.
     * @param field The field name
     */
    const onClearFieldAction = field => {
        switch (field) {
            case 'certificate':
                setCertificate(null);
                setCertificateFileName('');
                break;
            case 'picture':
                setPicture(null);
                setPictureFileName('');
                break;
            default:
                console.error('Unknown field cleared');
        }

    };

    /**
     * Handles the value changed event for the given field.
     * @param e The event
     * @param field The field name
     */
    const onFieldValueChanged = (e, field) => {
        switch (field) {
            case 'owner':
                setOwner(e.target.value);
                setOwnerRequired(false);
                break;
            case 'registration':
                setRegistration(e.target.value);
                setRegistrationRequired(false);
                setRegistrationUnique(false);
                break;
            case 'brand':
                setBrand(e.target.value);
                break;
            case 'model':
                setModel(e.target.value);
                break;
            case 'motorization':
                setMotorization(e.target.value);
                break;
            case 'releaseDate':
                setReleaseDate(e);
                break;
            case 'certificate':
                const certificateDocument = document.getElementById('CertificateInput');

                const certificateUrl = certificateDocument.value;
                setCertificateFileName(extractFileNameFromURL(certificateUrl));

                const certificateFile = certificateDocument.files[0];
                setCertificate(loadFileAsByteArray(certificateFile));
                break;
            case 'picture':
                const pictureDocument = document.getElementById('PictureInput');

                const pictureUrl = pictureDocument.value;
                setPictureFileName(extractFileNameFromURL(pictureUrl));

                const pictureFile = pictureDocument.files[0];
                setPicture(loadFileAsByteArray(pictureFile));
                break;
            case 'comments':
                setComments(e.target.value);
                break;
            default:
                console.error('Unknown field updated');
        }
    };

    /**
     * Handles the help button click action.
     */
    const onHelpButtonClick = () => {
        setHelp(!help);
    };

    /**
     * Handles the validate button click action.
     */
    const onValidateButtonClick = () => {
        const emptyValue = '';

        // Checks the form validation
        if (validateForm()) {
            const editedCar = {
                id: car.id,
                owner: emptyValue === owner ? null : owner,
                registration: emptyValue === registration ? null : registration,
                brand: emptyValue === brand ? null : brand,
                model: emptyValue === model ? null : model,
                motorization: emptyValue === motorization ? null : motorization,
                releaseDate,
                certificate,
                picture,
                comments: emptyValue === comments ? null : comments
            };

            onValidate(editedCar);
            onClose();

            setCertificateFileName(car.certificate ? currentCertificateLabel : '');
            setPictureFileName(car.picture ? currentImageLabel : '');
        }
    };

    /**
     * Returns the text field props for the release date picker depending on the current release date value.
     * @param props The initial props
     * @returns {*} The text field component with the final props
     */
    const releaseDateFieldProps = (props: TextFieldProps): any => <TextField InputProps={{ shrink: null !== releaseDate }} {...props} />;

    /**
     * Validates the form.
     * @returns {boolean} If the form is valid
     */
    const validateForm = () => {
        let isValid = true;

        // Validates the owner field
        if (null === owner || '' === owner) {
            setOwnerRequired(true);
            isValid = false;
        }

        // Validates the registration field
        if (null === registration || '' === registration) {
            setRegistrationRequired(true);
            isValid = false;
        } else if (registrations.includes(registration)) {
            setRegistrationUnique(true);
            isValid = false;
        }

        return isValid;
    };

    /**
     * Defines the adornment of the certificate field depending on the certificate being null or not.
     */
    const certificateFieldAdornment = certificate ? (<InputAdornment position='end'>
        <IconButton onClick={() => onClearFieldAction('certificate')}>
            <CancelIcon />
        </IconButton>
    </InputAdornment>) : (<InputAdornment position='end'>
        <IconButton component='label' variant='contained'>
            <BrowseIcon />
            <input accept='image/jpe, image/jpg, image/jpeg, image/gif, image/png, image/bmp,' id='CertificateInput'
                   onChange={e => onFieldValueChanged(e, 'certificate')} type='file' />
        </IconButton>
    </InputAdornment>);

    /**
     * Defines the adornment of the image field depending on the certificate being null or not.
     */
    const pictureFieldAdornment = picture ? (<InputAdornment position='end'>
        <IconButton onClick={() => onClearFieldAction('picture')}>
            <CancelIcon />
        </IconButton>
    </InputAdornment>) : (<InputAdornment position='end'>
        <IconButton component='label' variant='contained'>
            <BrowseIcon />
            <input accept='image/jpe, image/jpg, image/jpeg, image/gif, image/png, image/bmp,' id='PictureInput'
                   onChange={e => onFieldValueChanged(e, 'picture')} type='file' />
        </IconButton>
    </InputAdornment>);

    return (<Dialog className={className} id='EditCarModal' onClose={onCancelAction} open={open}>
        <DialogTitle className='Title'>
            Ajout d'une voiture

            <IconButton className='HelpButton' color='primary' onClick={onHelpButtonClick}>
                <HelpIcon />
            </IconButton>
        </DialogTitle>

        <DialogContent>
            <DialogContentText className={!help && 'Instructions_hidden'}>
                Veuillez éditer les champs ci-dessous puis cliquer sur 'Valider' afin d'éditer la voiture actuelle.
                Les champs annotés du symbole * sont obligatoires.
            </DialogContentText>

            <Grid alignItems='center' container direction='column' justify='center'>
                <Grid alignItems='center' container justify='space-between' spacing={2}>
                    <Grid item xs>
                        <TextField className={`Field ${ownerRequired && 'Field_error'}`}
                                   error={ownerRequired}
                                   fullWidth
                                   helperText={ownerRequired && ownerRequiredHelperText}
                                   label='Propriétaire'
                                   onChange={e => onFieldValueChanged(e, 'owner')}
                                   required
                                   value={owner}
                                   variant='outlined' />
                    </Grid>

                    <Grid item xs>
                        <TextField className={`Field ${(registrationRequired || registrationUnique) && 'Field_error'}`}
                                   error={registrationRequired || registrationUnique}
                                   fullWidth
                                   helperText={registrationRequired ? registrationRequiredHelperText : registrationUnique
                                       && registrationUniqueHelperText}
                                   label="Numéro d'immatriculation"
                                   onChange={e => onFieldValueChanged(e, 'registration')}
                                   required
                                   value={registration}
                                   variant='outlined' />
                    </Grid>
                </Grid>

                <Grid alignItems='center' container justify='space-between' spacing={2}>
                    <Grid item xs>
                        <TextField className='Field' fullWidth label='Marque'
                                   onChange={e => onFieldValueChanged(e, 'brand')} value={brand} variant='outlined' />
                    </Grid>

                    <Grid item xs>
                        <TextField className='Field' fullWidth label='Modèle'
                                   onChange={e => onFieldValueChanged(e, 'model')} value={model} variant='outlined' />
                    </Grid>
                </Grid>

                <Grid alignItems='center' container justify='space-between' spacing={2}>
                    <Grid item xs>
                        <TextField className='Field' fullWidth label='Motorisation'
                                   onChange={e => onFieldValueChanged(e, 'motorization')} value={motorization} variant='outlined' />
                    </Grid>

                    <Grid item xs>
                        <DatePicker autoOk
                                    cancelLabel='Annuler'
                                    className='Field'
                                    clearable
                                    clearLabel='Supprimer'
                                    disableFuture
                                    disableToolbar
                                    fullWidth
                                    inputVariant='outlined'
                                    label='Date de mise en circulation'
                                    labelFunc={formatDateLabel}
                                    onChange={e => onFieldValueChanged(e, 'releaseDate')}
                                    TextFieldComponent={releaseDateFieldProps}
                                    value={releaseDate}
                                    variant='dialog'
                                    views={[ 'year', 'month' ]} />
                    </Grid>
                </Grid>
            </Grid>

            <FormControl className='Field' fullWidth variant='outlined'>
                <InputLabel htmlFor='CertificateField'>Carte grise</InputLabel>

                <OutlinedInput id='CertificateField' endAdornment={certificateFieldAdornment} labelWidth={80} readOnly value={certificateFileName} />
            </FormControl>

            <FormControl className='Field' fullWidth variant='outlined'>
                <InputLabel htmlFor='PictureField'>Image</InputLabel>

                <OutlinedInput id='PictureField' endAdornment={pictureFieldAdornment} labelWidth={46} readOnly value={pictureFileName} />
            </FormControl>

            <TextField className='Field' fullWidth label='Commentaires' multiline onChange={e => onFieldValueChanged(e, 'comments')}
                       rowsMax={4} value={comments} variant='outlined' />
        </DialogContent>

        <DialogActions>
            <Button color='primary' onClick={onCancelAction}>
                Annuler
            </Button>

            <Button autoFocus color='primary' onClick={onValidateButtonClick}>
                Valider
            </Button>
        </DialogActions>
    </Dialog>);
}

EditCarModal.propTypes = {
    className: PropTypes.string,
    open: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
    onValidate: PropTypes.func.isRequired,
    registrations: PropTypes.arrayOf(PropTypes.string)
};

EditCarModal.defaultProps = {
    className: '',
    registrations: []
};

export default EditCarModal;
