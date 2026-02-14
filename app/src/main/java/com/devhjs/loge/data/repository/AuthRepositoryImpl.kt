package com.devhjs.loge.data.repository

import com.devhjs.loge.domain.repository.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Github
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : AuthRepository {

    override suspend fun signInWithGithub() {
        supabaseClient.auth.signInWith(Github)
    }

    override suspend fun signOut() {
        supabaseClient.auth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return supabaseClient.auth.currentSessionOrNull() != null
    }

    override fun getCurrentUserEmail(): String? {
        return supabaseClient.auth.currentUserOrNull()?.email
    }

    override fun getCurrentUserUid(): String? {
        return supabaseClient.auth.currentUserOrNull()?.id
    }
}
