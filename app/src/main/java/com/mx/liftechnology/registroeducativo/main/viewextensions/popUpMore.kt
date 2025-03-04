package com.mx.liftechnology.registroeducativo.main.viewextensions

import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.R

/** showPopUpMore - show the popUpMenu
 * @author pelkidev
 * @since 1.0.0
 */
fun <T> Fragment.showPopUpMore(
    view: View,
    item: T,
    onDelete: (T) -> Unit, // Acción específica para eliminar
    onEditNavigate: () -> Unit // Acción para editar
) {
    val popupMenu = PopupMenu(requireContext(), view)
    popupMenu.menuInflater.inflate(R.menu.options_menu, popupMenu.menu)

    popupMenu.setOnMenuItemClickListener { menuItem ->
        when (menuItem.itemId) {
            R.id.menu_edit -> {
                onEditNavigate() // Navegación personalizada
                true
            }
            R.id.menu_delete -> {
                showDeleteConfirmationDialog(item, onDelete) // Diálogo genérico
                true
            }
            else -> false
        }
    }
    popupMenu.show()
}

/** showDeleteConfirmationDialog - show a dialog to confirm deletes
 * @author pelkidev
 * @since 1.0.0
 */
fun <T> Fragment.showDeleteConfirmationDialog(item: T, onDelete: (T) -> Unit) {
    val title = when (item) {
        is ModelStudentDomain -> "Eliminar Estudiante"
        is ModelFormatSubjectDomain -> "Eliminar Materia"
        else -> "Eliminar"
    }

    val message = when (item) {
        is ModelStudentDomain -> "¿Seguro que quieres eliminar a ${item.name}?"
        is ModelFormatSubjectDomain -> "¿Estás seguro de que deseas eliminar ${item.name}?"
        else -> "¿Seguro que quieres eliminar este elemento?"
    }

    AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Eliminar") { _, _ -> onDelete(item) }
        .setNegativeButton("Cancelar", null)
        .show()
}
