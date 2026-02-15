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

    // GitHub 프로필 정보: Supabase Auth 세션의 userMetadata에서 읽어옴
    override fun getGithubName(): String? {
        return supabaseClient.auth.currentUserOrNull()
            ?.userMetadata?.get("full_name")?.toString()
    }

    override fun getGithubAvatarUrl(): String? {
        return supabaseClient.auth.currentUserOrNull()
            ?.userMetadata?.get("avatar_url")?.toString()
    }

    override fun getGithubId(): String? {
        return supabaseClient.auth.currentUserOrNull()
            ?.userMetadata?.get("user_name")?.toString()
    }
}
