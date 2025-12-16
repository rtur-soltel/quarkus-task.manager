package es.juntadeandalucia.agapa.infrastructure.controller;

import es.juntadeandalucia.agapa.application.dto.TareaDTO;
import es.juntadeandalucia.agapa.application.mapper.TareaMapper;
import es.juntadeandalucia.agapa.application.service.TareasService;
import es.juntadeandalucia.agapa.domain.model.Tarea;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Path("/tareas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tareas", description = "API de gestión de tareas")
public class TareasController {

    @Inject
    TareasService tareasService;

    @Inject
    TareaMapper tareaMapper;

    @GET
    @Operation(summary = "Listar todas las tareas", description = "Obtiene una lista completa de todas las tareas registradas en el sistema")
    @APIResponse(responseCode = "200", description = "Lista de tareas recuperada exitosamente", 
                 content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                                   schema = @Schema(implementation = TareaDTO.class, type = SchemaType.ARRAY)))
    public List<TareaDTO> listar() {
        return tareaMapper.toDTOList(tareasService.listarTareas());
    }

    @POST
    @Operation(summary = "Crear una nueva tarea", description = "Crea una nueva tarea en el sistema con los datos proporcionados")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Tarea creada exitosamente", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                                       schema = @Schema(implementation = TareaDTO.class))),
        @APIResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @APIResponse(responseCode = "409", description = "Ya existe una tarea con ese título")
    })
    public Response crear(
        @Valid
        @Parameter(description = "Datos de la tarea a crear", required = true, 
                   schema = @Schema(implementation = TareaDTO.class))
        TareaDTO dto) {
        try {
            Tarea tarea = tareaMapper.toEntity(dto);
            Tarea nuevaTarea = tareasService.crearTarea(tarea);
            return Response.status(Response.Status.CREATED)
                    .entity(tareaMapper.toDTO(nuevaTarea))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}/done")
    @Operation(summary = "Marcar tarea como completada", description = "Marca una tarea específica como completada/done")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Tarea marcada como completada", 
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                                       schema = @Schema(implementation = TareaDTO.class))),
        @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public Response marcarDone(
        @Parameter(description = "ID de la tarea a marcar como completada", required = true, example = "1")
        @PathParam("id") Long id) {
        Optional<Tarea> tareaOpt = tareasService.marcarComoCompletada(id);
        if (tareaOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(tareaMapper.toDTO(tareaOpt.get())).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar una tarea", description = "Elimina una tarea específica del sistema")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Tarea eliminada exitosamente"),
        @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public Response eliminar(
        @Parameter(description = "ID de la tarea a eliminar", required = true, example = "1")
        @PathParam("id") Long id) {
        boolean eliminada = tareasService.eliminarTarea(id);
        if (!eliminada) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Consultar una tarea", description = "Obtiene una tarea específica del sistema")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Tarea consultada exitosamente",
                     content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                                       schema = @Schema(implementation = TareaDTO.class))),
        @APIResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public Response obtener(
        @Parameter(description = "ID de la tarea a consultar", required = true, example = "1")
        @PathParam("id") Long id) {
        Optional<Tarea> tareaOpt = tareasService.obtenerTareaPorId(id);
        if (tareaOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(tareaMapper.toDTO(tareaOpt.get())).build();
    }
}
