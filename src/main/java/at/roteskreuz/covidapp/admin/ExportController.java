package at.roteskreuz.covidapp.admin;


import at.roteskreuz.covidapp.model.ApiResponse;
import at.roteskreuz.covidapp.service.BatchService;
import at.roteskreuz.covidapp.service.WorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for generating batches and export files
 *
 * @author Zoltán Puskai
 */
@RestController
@RequestMapping(path = "/admin/export")
@RequiredArgsConstructor
@Api(tags = "Export", description = "Endpoint that exports batches.")
public class ExportController {
	
	private final BatchService batchService;
	private final WorkerService workerService;

	/**
	 * Creates batches
	 * @return Api response
	 */
	@ApiOperation(value = "Creates batches.)", authorizations = {
		@Authorization(value = "AuthorizationKey")})
	@ApiImplicitParams({
	   @ApiImplicitParam(name = "X-AppId", value = "Application id", required = true, dataType = "string", paramType = "header")		
	 })		
	@ApiResponses(value = {
		@io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = ApiResponse.class),
		@io.swagger.annotations.ApiResponse(code = 400, message = "Bad request"),
		@io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden"),
		@io.swagger.annotations.ApiResponse(code = 500, message = "Internal server error")})
	@GetMapping(value = "/create-batches", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> createBatches()  {
		ApiResponse response = batchService.createBratches();
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	/**
	 * Exports exposures
	 * @return Api response
	 * @throws Exception 
	 */
	@ApiOperation(value = "Creates the export files.)", authorizations = {
		@Authorization(value = "AuthorizationKey")})
	@ApiImplicitParams({
	   @ApiImplicitParam(name = "X-AppId", value = "Application id", required = true, dataType = "string", paramType = "header")		
	 })		
	@ApiResponses(value = {
		@io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = ApiResponse.class),
		@io.swagger.annotations.ApiResponse(code = 400, message = "Bad request"),
		@io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden"),
		@io.swagger.annotations.ApiResponse(code = 500, message = "Internal server error")})	
	@GetMapping(value = "/do-work", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> doWork() throws Exception  {
		ApiResponse response = workerService.doWork();
		return ResponseEntity.status(response.getStatus()).body(response);
	}
}
