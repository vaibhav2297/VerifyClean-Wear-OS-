package com.qualflow.verifyclean.core.utils.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.qualflow.verifyclean.core.utils.VoidCallback

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestAppPermissions(
    appPermissions: AppPermissions,
    onPermissionRevoked: VoidCallback,
    navigateToSetting: VoidCallback,
    allPermissionsGranted: VoidCallback
) {

    var permissionAlreadyRequested by rememberSaveable {
        mutableStateOf(false)
    }

    val multiplePermissionsState = rememberMultiplePermissionsState(
        permissions = appPermissions.permissions
    ) {

        permissionAlreadyRequested = true

        if (!it.containsValue(false)) {
            allPermissionsGranted()
        }
    }

    if (!permissionAlreadyRequested && !multiplePermissionsState.shouldShowRationale) {
        SideEffect {
            multiplePermissionsState.launchMultiplePermissionRequest()
        }
    } else if (multiplePermissionsState.shouldShowRationale) {
        //TODO Handle this
        /*SimulatorAlertDialog(
            title = stringResource(id = R.string.permission_required_title),
            description = stringResource(id = R.string.permission_required_desc),
            positiveButtonText = stringResource(R.string.allow),
            negativeButtonText = stringResource(R.string.deny),
            onDismiss = onPermissionRevoked
        ) {
            multiplePermissionsState.launchMultiplePermissionRequest()
        }*/

    } else {
        //permanently denied
        //TODO Handle this
        /*SimulatorAlertDialog(
            title = stringResource(id = R.string.permission_permanently_denied_title),
            description = stringResource(id = R.string.permission_permanently_denied_desc),
            positiveButtonText = stringResource(R.string.go_to_setting),
            negativeButtonText = stringResource(R.string.cancel),
            onDismiss = onPermissionRevoked,
            onConfirmation = navigateToSetting
        )*/
    }
}