import { z } from "zod";

interface UserAuthenticationRequestDTO {
  email: string;
  password: string;
}

const validationSchema = z.object({
  email: z.string()
    .email({ message: "O email informado é inválido." })
    .min(5, { message: "O email deve conter no mínimo 5 caracteres." })
    .max(80, { message: "O email deve conter no máximo 80 caracteres." }),
  password: z.string()
    .min(6, { message: "A senha deve conter no mínimo 6 caracteres." })
});

export default UserAuthenticationRequestDTO;
export { validationSchema };
