import UserRegistrationRequestDTO from "../dtos/requests/user-registration-request.dto";
import ApiResponseDTO from "../dtos/responses/api-response.dto";
import { post } from "./base-http.service";

const userRegistrationService = async (content: UserRegistrationRequestDTO): Promise<ApiResponseDTO<void>> => {
  return await post<UserRegistrationRequestDTO, ApiResponseDTO<void>>(
    'auth/register',
    content
  );
}

export default userRegistrationService;
