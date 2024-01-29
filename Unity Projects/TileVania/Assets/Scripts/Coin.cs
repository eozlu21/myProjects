using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Coin : MonoBehaviour
{
    [SerializeField] private AudioSource audioSource;
    [SerializeField] private int coinPickupPoints = 100;
    private bool isPickedUp;
    private void OnTriggerEnter2D(Collider2D other)
    {
        if (!other.CompareTag("Player") || isPickedUp)
        {
            return;
        }

        isPickedUp = true;
        FindObjectOfType<GameSession>().IncrementScore(coinPickupPoints);
        AudioSource.PlayClipAtPoint(audioSource.clip, Camera.main.transform.position);
        Destroy(gameObject);
    }
}
